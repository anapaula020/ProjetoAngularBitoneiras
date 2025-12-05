import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario.model';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UsuarioService {
    private baseUrl = 'http://localhost:8000/usuarios';

    constructor(private httpClient: HttpClient) { }

    findAll(page?: number,pageSize?: number): Observable<Usuario[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<Usuario[]>(this.baseUrl, {params});
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    findById(id: number): Observable<Usuario> {
        return this.httpClient.get<Usuario>(`${this.baseUrl}/${id}`);
    }

    insert(usuario: Usuario): Observable<Usuario> {
        const data = {
            username: usuario.username, 
            email: usuario.email, 
            senha: usuario.senha, 
            cpf: usuario.cpf, 
            sexo: usuario.sexo
        };
        return this.httpClient.post<Usuario>(this.baseUrl,data);
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
    

    update(usuario: Usuario): Observable<Usuario> {
        const data = {
            username: usuario.username, 
            email: usuario.email, 
            senha: usuario.senha, 
            cpf: usuario.cpf, 
            endereco: usuario.endereco, 
            sexo: usuario.sexo
        };
        return this.httpClient.put<Usuario>(`${this.baseUrl}/${usuario.id}`,data);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }

}
