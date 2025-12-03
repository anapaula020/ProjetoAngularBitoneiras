import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EscritorNovel } from "../models/escritorNovel.model";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: "root" })

export class EscritorNovelService {
    private baseUrl = "http://localhost:8000/escritorNovel";

    constructor(private httpClient: HttpClient) { }

    findAll(page?: number,pageSize?: number): Observable<EscritorNovel[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<EscritorNovel[]>(this.baseUrl, {params});
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    findById(id: number): Observable<EscritorNovel> {
        return this.httpClient.get<EscritorNovel>(`${this.baseUrl}/${id}`);
    }

    insert(escritor: EscritorNovel): Observable<EscritorNovel> {
        return this.httpClient.post<EscritorNovel>(this.baseUrl, escritor);
    }

    update(escritor: EscritorNovel): Observable<EscritorNovel> {
        return this.httpClient.put<EscritorNovel>(`${this.baseUrl}/${escritor.id}`,escritor);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }
}