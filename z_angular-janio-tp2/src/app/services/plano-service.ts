import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Plano } from '../models/plano.model';

@Injectable({
  providedIn: 'root'
})
export class PlanoService {
private baseUrl = 'http://localhost:8080/planos';

  constructor(private httpClient: HttpClient) { }

  getPlanos(page?: number, pageSize?: number): Observable<Plano[]> {
    let params = {};

    if ((page !== undefined) && (pageSize !== undefined)) {
      
      params = {
        page: page.toString(),
        page_size: pageSize.toString()
      }
    }

    return this.httpClient.get<Plano[]>(this.baseUrl, {params});
  }

  buscarPorId(id: string): Observable<Plano> {
    return this.httpClient.get<Plano>(`${this.baseUrl}/${id}`);
  }

  incluir(plano: any): Observable<Plano> {
    return this.httpClient.post<Plano>(this.baseUrl, plano);
  }

  alterar(plano: any): Observable<any> {
    return this.httpClient.put<any>(`${this.baseUrl}/${plano.id}`, plano);
  }

  excluir(id: number): Observable<any> {
    return this.httpClient.delete<any>(`${this.baseUrl}/${id}`);
  }

  count(): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}/count`);
  }

  getUrlImage(nomeImagem: string|null|undefined): string {
    return `${this.baseUrl}/image/download/${nomeImagem}`;
  }

}

