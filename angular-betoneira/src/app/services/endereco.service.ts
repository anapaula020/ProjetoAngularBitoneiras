import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Endereco } from "../models/endereco.model";

@Injectable({ providedIn: "root" })

export class EscritorNovelService {
    private baseUrl = "http://localhost:8000/enderecos";

    constructor(private httpClient: HttpClient) { }

    findAll(page?: number,pageSize?: number): Observable<Endereco[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<Endereco[]>(this.baseUrl, {params});
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    findById(id: number) {
        return this.httpClient.get<Endereco[]>(`${this.baseUrl}/${id}`);
    }

    findMyAddresses() {
        return this.httpClient.get<Endereco[]>(`${this.baseUrl}/`);
    }

    insert(endereco: Endereco): Observable<Endereco> {
        return this.httpClient.post<Endereco>(this.baseUrl, endereco);
    }

    update(escritor: Endereco): Observable<Endereco> {
        return this.httpClient.put<Endereco>(`${this.baseUrl}/${escritor.id}`,escritor);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }
}