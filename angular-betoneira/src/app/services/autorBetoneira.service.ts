import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AutorBetoneira } from "../models/autorBetoneira.model";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: "root" })

export class AutorService {
    private baseUrl = "http://localhost:8000/autorBetoneira";

    constructor(private httpClient: HttpClient) { }

    findAll(page?: number,pageSize?: number): Observable<AutorBetoneira[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<AutorBetoneira[]>(this.baseUrl, {params});
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    insert(autorBetoneira: AutorBetoneira): Observable<AutorBetoneira> {
        const data = {
            nome: autorBetoneira.nome,
            anoNascimento: autorBetoneira.anoNascimento,
            nacionalidade: autorBetoneira.nacionalidade,
            sexo: autorBetoneira.sexo
        };
        return this.httpClient.post<AutorBetoneira>(this.baseUrl, data);
    }

    update(autorBetoneira: AutorBetoneira): Observable<AutorBetoneira> {
        const data = {
            nome: autorBetoneira.nome,
            anoNascimento: autorBetoneira.anoNascimento,
            nacionalidade: autorBetoneira.nacionalidade,
            sexo: autorBetoneira.sexo
        };
        return this.httpClient.put<AutorBetoneira>(`${this.baseUrl}/${autorBetoneira.id}`, data);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }
    findById(id: number): Observable<AutorBetoneira> {
        return this.httpClient.get<AutorBetoneira>(`${this.baseUrl}/${id}`);
    }
}