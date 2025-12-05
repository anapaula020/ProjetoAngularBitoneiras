import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Usuario } from "../models/usuario.model";

@Injectable({
    providedIn: "root"
})
export class UsuarioService {
    private baseUrl = "http://localhost:8080/users";

    constructor(private http: HttpClient) { }

    getMyUserInfo(): Observable<Usuario> {
        return this.http.get<Usuario>(`${this.baseUrl}/me`);
    }
}