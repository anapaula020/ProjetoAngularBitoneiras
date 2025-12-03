import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AutorManga } from "../models/autorManga.model";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: "root" })

export class AutorService {
    private baseUrl = "http://localhost:8000/autorManga";

    constructor(private httpClient: HttpClient) { }

    findAll(page?: number,pageSize?: number): Observable<AutorManga[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<AutorManga[]>(this.baseUrl, {params});
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    insert(autorManga: AutorManga): Observable<AutorManga> {
        const data = {
            nome: autorManga.nome,
            anoNascimento: autorManga.anoNascimento,
            nacionalidade: autorManga.nacionalidade,
            sexo: autorManga.sexo
        };
        return this.httpClient.post<AutorManga>(this.baseUrl, data);
    }

    update(autorManga: AutorManga): Observable<AutorManga> {
        const data = {
            nome: autorManga.nome,
            anoNascimento: autorManga.anoNascimento,
            nacionalidade: autorManga.nacionalidade,
            sexo: autorManga.sexo
        };
        return this.httpClient.put<AutorManga>(`${this.baseUrl}/${autorManga.id}`, data);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }
    findById(id: number): Observable<AutorManga> {
        return this.httpClient.get<AutorManga>(`${this.baseUrl}/${id}`);
    }
}