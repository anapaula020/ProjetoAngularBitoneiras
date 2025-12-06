import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cliente } from '../models/cliente.model';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ClienteService {
    private baseUrl = 'http://localhost:8080/usuarios';

    constructor(private httpClient: HttpClient) { }

    findAll(page?: number,pageSize?: number): Observable<Cliente[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<Cliente[]>(this.baseUrl, {params});
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    findById(id: number): Observable<Cliente> {
        return this.httpClient.get<Cliente>(`${this.baseUrl}/${id}`);
    }

    insert(usuario: Cliente): Observable<Cliente> {
        const data = {
            username: usuario.username, 
            email: usuario.email, 
            senha: usuario.senha, 
            cpf: usuario.cpf
        };
        return this.httpClient.post<Cliente>(this.baseUrl,data);
    }

    updateSenha(senhaAntiga: string, repeticao: string, senhaNova: string): Observable<any> {
        const senhaDTO = {
            senhaAtual: senhaAntiga,
            novaSenha: senhaNova,
            confirmacao: repeticao,
        };
        return this.httpClient.patch(`${this.baseUrl}/updateSenha`, senhaDTO);
    }    
    
    updateEmail(email: string): Observable<any> {
        return this.httpClient.patch(`${this.baseUrl}/updateEmail`, { email });
    }

    updateEndereco(rua: string, numero: string, cep: string, cidade: string, estado: string): Observable<any> {
        const enderecoDTO = {
            rua: rua,
            numero: numero,
            cep: cep,
            cidade: cidade,
            estado: estado
        };
        return this.httpClient.patch(`${this.baseUrl}/updateEndereco`, enderecoDTO);
    }
    

    update(usuario: Cliente): Observable<Cliente> {
        const data = {
            username: usuario.username, 
            email: usuario.email, 
            senha: usuario.senha, 
            cpf: usuario.cpf, 
            endereco: usuario.endereco
        };
        return this.httpClient.put<Cliente>(`${this.baseUrl}/${usuario.id}`,data);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }

}
