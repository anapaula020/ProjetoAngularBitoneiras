import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Novel } from '../models/novel.model';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class NovelService {
    private baseUrl = 'http://localhost:8000/novel';

    constructor(private httpClient: HttpClient) { }

    findAll(page?: number,pageSize?: number): Observable<Novel[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<Novel[]>(this.baseUrl,{ params });
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    findById(id: number): Observable<Novel> {
        return this.httpClient.get<Novel>(`${this.baseUrl}/${id}`);
    }

    findByName(term: string): Observable<Novel[]> {
        return this.httpClient.get<Novel[]>(`${this.baseUrl}/name/${term}`);
    }

    insert(novel: Novel): Observable<Novel> {
        const data = {
            nome: novel.nome,
            idAutor: novel.idAutor,
            genero: novel.genero,
            sinopse: novel.sinopse,
            lancamento: novel.lancamento,
            estoque: novel.estoque,
            preco: novel.preco,
            paginas: novel.paginas,
            capitulos: novel.capitulos
        };
        return this.httpClient.post<Novel>(this.baseUrl,data);
    }

    update(novel: Novel): Observable<Novel> {
        const data = {
            nome: novel.nome,
            idAutor: novel.idAutor,
            genero: novel.genero,
            sinopse: novel.sinopse,
            lancamento: novel.lancamento,
            estoque: novel.estoque,
            preco: novel.preco,
            paginas: novel.paginas,
            capitulos: novel.capitulos
        };
        return this.httpClient.put<Novel>(`${this.baseUrl}/${novel.id}`,data);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }

    searchNovels(query: string): Observable<Novel[]> {
        return this.httpClient.get<Novel[]>(`${this.baseUrl}?search=${query}`);
    }

    toImageUrl(id: number, imagem: string): string {
        return `${this.baseUrl}/${id}/image/download/${imagem}`;
    }
}