import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Betoneira } from '../models/betoneira.model';

@Injectable({ providedIn: 'root' })
export class BetoneiraService {
    private baseUrl = 'http://localhost:8000/betoneira';

    constructor(private httpClient: HttpClient) { }

    uploadImage(id: number,imageUrl: string,imagem: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('imageUrl',imageUrl);
        formData.append('imagem',imagem,imagem.name);

        return this.httpClient.patch<Betoneira>(`${this.baseUrl}/${id}/image/upload`,formData);
    }

    findAll(page?: number,pageSize?: number): Observable<Betoneira[]> {
        let params = {};
        if(page !== undefined && pageSize !== undefined) {
            params = {
                page: page.toString(),
                pageSize: pageSize.toString()
            }
        }
        return this.httpClient.get<Betoneira[]>(this.baseUrl,{ params });
    }

    findByFabricante(id: number): Observable<Betoneira[]> {
        return this.httpClient.get<Betoneira[]>(`${this.baseUrl}/fabricante/${id}`);
    }

    findByPrice(price: number,price2: number): Observable<Betoneira[]> {
        return this.httpClient.get<Betoneira[]>(`${this.baseUrl}/price/${price}/${price2}`);
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    findById(id: number): Observable<Betoneira> {
        return this.httpClient.get<Betoneira>(`${this.baseUrl}/${id}`);
    }

    findByName(term: string): Observable<Betoneira[]> {
        return this.httpClient.get<Betoneira[]>(`${this.baseUrl}/name/${term}`);
    }

    insert(betoneira: Betoneira): Observable<Betoneira> {
        const data = {
            nome: betoneira.nome, 
            descricao: betoneira.descricao, 
            preco: betoneira.preco, 
            quantidadeEstoque: betoneira.quantidadeEstoque, 
            modelo: betoneira.modelo, 
            marca: betoneira.marca, 
            capacidade: betoneira.capacidade, 
            tipoBetoneira: betoneira.tipoBetoneira, 
            idFabricante: betoneira.idFabricante, 
            imageUrl: betoneira.imageUrl
        };
        return this.httpClient.post<Betoneira>(this.baseUrl,data);
    }

    update(betoneira: Betoneira): Observable<Betoneira> {
        const data = {
            nome: betoneira.nome, 
            descricao: betoneira.descricao, 
            preco: betoneira.preco, 
            quantidadeEstoque: betoneira.quantidadeEstoque, 
            modelo: betoneira.modelo, 
            marca: betoneira.marca, 
            capacidade: betoneira.capacidade, 
            tipoBetoneira: betoneira.tipoBetoneira, 
            idFabricante: betoneira.idFabricante, 
            imageUrl: betoneira.imageUrl
        };
        return this.httpClient.put<Betoneira>(`${this.baseUrl}/${betoneira.id}`,data);
    }

    delete(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.baseUrl}/${id}`);
    }

    searchBetoneiras(query: string): Observable<Betoneira[]> {
        return this.httpClient.get<Betoneira[]>(`${this.baseUrl}?search=${query}`);
    }

    toImageUrl(imagem: string): string {
        if(!imagem) {
            return 'semimagem.png';
        }
        return `${this.baseUrl}/image/download/${imagem}`;
    }
}