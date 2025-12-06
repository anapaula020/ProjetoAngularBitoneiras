import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { JwtHelperService } from "@auth0/angular-jwt";
import { BehaviorSubject,Observable,tap } from "rxjs";
import { LocalStorageService } from "./local-storage.service";
import { Cliente } from "../models/cliente.model";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private baseUrl = 'http://localhost:8000/auth';
    private tokenKey = 'jwt_token';
    private clienteLogadoKey = 'cliente_logado';
    private clienteLogadoSubject = new BehaviorSubject<Cliente | null>(null);

    constructor(private httpClient: HttpClient,private localStorageService: LocalStorageService,private jwtHelper: JwtHelperService) {
        this.init();
    }

    private init(): void {
        const cliente = this.localStorageService.getItem(this.clienteLogadoKey);
        if(cliente) {
            this.clienteLogadoSubject.next(cliente);
        }
    }

    public getUserRole(): string {
        const token = this.getToken();
        if(!token) {
            return '';
        }
        return this.jwtHelper.decodeToken(token)?.groups?.some((e: string) => e.toLowerCase().startsWith('admin')) ? 'admin' : 'user';
    }


    public login(username: string,senha: string): Observable<any> {
        return this.httpClient.post(`${this.baseUrl}`,{ username,senha },{ observe: 'response' }).pipe(
            tap((res: any) => {
                if(res.headers.get('Authorization')) {
                    this.setToken(res.headers.get('Authorization'));
                    const clienteLogado = res.body;
                    if(clienteLogado) {
                        this.setClienteLogado(clienteLogado);
                        this.clienteLogadoSubject.next(clienteLogado);
                    }
                }
            })
        )
    }

    setClienteLogado(cliente: Cliente): void {
        this.localStorageService.setItem(this.clienteLogadoKey,cliente);
    }

    setToken(token: string): void {
        this.localStorageService.setItem(this.tokenKey,token);
    }

    getClienteLogado() {
        return this.clienteLogadoSubject.asObservable();
    }

    getToken(): string | null {
        return this.localStorageService.getItem(this.tokenKey);
    }

    removeToken(): void {
        this.localStorageService.removeItem(this.tokenKey);
    }

    removeUser(): void {
        this.localStorageService.removeItem(this.clienteLogadoKey);
        this.clienteLogadoSubject.next(null);
    }

    isTokenExpired(): boolean {
        const token = this.getToken();
        if(!token) {
            return true;
        }
        try {
            return this.jwtHelper.isTokenExpired(token);
        } catch(error) {
            return true;
        }
    }
}