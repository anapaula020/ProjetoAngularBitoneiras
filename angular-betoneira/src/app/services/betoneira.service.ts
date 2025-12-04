import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Betoneira } from '../models/betoneira.model';
import { GeneroBetoneira } from '../models/generoBetoneira.model';

@Injectable({ providedIn: 'root' })
export class BetoneiraService {
    private baseUrl = 'http://localhost:8000/betoneira';

    constructor(private httpClient: HttpClient) { }

    findByAuthor(id: number): Observable<Betoneira[]> {
        return this.httpClient.get<Betoneira[]>(`${this.baseUrl}/autor/${id}`);
    }

    findByPrice(price: number,price2: number): Observable<Betoneira[]> {
        return this.httpClient.get<Betoneira[]>(`${this.baseUrl}/price/${price}/${price2}`);
    }
    findGeneros(): Observable<GeneroBetoneira[]> {
        return this.httpClient.get<GeneroBetoneira[]>(`${this.baseUrl}/generos`);
    }

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

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    findById(id: number): Observable<Betoneira> {
        return this.httpClient.get<Betoneira>(`${this.baseUrl}/${id}`);
    }

    findByName(term: string): Observable<Betoneira[]> {
        return this.httpClient.get<Betoneira[]>(`${this.baseUrl}/name/${term}`);
    }

    findByGenre(id: number): Observable<Betoneira[]> {
        return this.httpClient.get<Betoneira[]>(`${this.baseUrl}/genero/${id}`);
    }

    insert(Betoneira: Betoneira): Observable<Betoneira> {
        const data = {
            nome: Betoneira.nome,
            idAutor: Betoneira.idAutor,
            genero: Betoneira.genero.id,
            sinopse: Betoneira.sinopse,
            lancamento: Betoneira.lancamento,
            estoque: Betoneira.estoque,
            preco: Betoneira.preco,
            color: Betoneira.color,
            paginas: Betoneira.paginas,
            imageUrl: Betoneira.imageUrl
        };
        return this.httpClient.post<Betoneira>(this.baseUrl,data);
    }

    update(Betoneira: Betoneira): Observable<Betoneira> {
        const data = {
            nome: Betoneira.nome,
            idAutor: Betoneira.idAutor,
            genero: Betoneira.genero.id,
            sinopse: Betoneira.sinopse,
            lancamento: Betoneira.lancamento,
            estoque: Betoneira.estoque,
            color: Betoneira.color,
            preco: Betoneira.preco,
            paginas: Betoneira.paginas,
            imageUrl: Betoneira.imageUrl
        };
        return this.httpClient.put<Betoneira>(`${this.baseUrl}/${Betoneira.id}`,data);
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
