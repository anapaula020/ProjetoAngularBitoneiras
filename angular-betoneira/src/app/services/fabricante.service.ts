import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Fabricante } from "../models/fabricante";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: "root" })

export class FabricanteService {
    private baseUrl = "http://localhost:8000/fabricante";

    constructor(private httpClient: HttpClient) { }

    findAll(page?: number,pageSize?: number): Observable<Fabricante[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<Fabricante[]>(this.baseUrl, {params});
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    insert(fabricante: Fabricante): Observable<Fabricante> {
        const data = {
            nome: fabricante.nome,
            cnpj: fabricante.cnpj
        };
        return this.httpClient.post<Fabricante>(this.baseUrl, data);
    }

    update(fabricante: Fabricante): Observable<Fabricante> {
        const data = {
            nome: fabricante.nome,
            cnpj: fabricante.cnpj
        };
        return this.httpClient.put<Fabricante>(`${this.baseUrl}/${fabricante.id}`, data);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }
    findById(id: number): Observable<Fabricante> {
        return this.httpClient.get<Fabricante>(`${this.baseUrl}/${id}`);
    }
}