import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Estado } from '../models/estado.model';

@Injectable({
  providedIn: 'root'
})
export class EstadoService {
  private baseUrl = 'http://localhost:8080/estados';

  constructor(private httpClient: HttpClient) { }

  getEstados(page?: number, pageSize?: number): Observable<Estado[]> {
    let params = {};

    console.log(page !== undefined);
    console.log(pageSize !== undefined);

    if ((page !== undefined) && (pageSize !== undefined)) {
      
      params = {
        page: page.toString(),
        page_size: pageSize.toString()
      }
    }

    console.log(this.baseUrl);
    console.log(params);

    return this.httpClient.get<Estado[]>(this.baseUrl, {params});
    //return this.httpClient.get<Estado[]>(`${this.baseUrl}?page=${page}&pageSize=${pageSize}`);
  }

  buscarPorId(id: string): Observable<Estado> {
    return this.httpClient.get<Estado>(`${this.baseUrl}/${id}`);
  }

  incluir(estado: any): Observable<Estado> {
    return this.httpClient.post<Estado>(this.baseUrl, estado);
  }

  alterar(estado: any): Observable<any> {
    return this.httpClient.put<any>(`${this.baseUrl}/${estado.id}`, estado);
  }

  excluir(estado: Estado): Observable<any> {
    return this.httpClient.delete<any>(`${this.baseUrl}/${estado.id}`);
  }

  count(): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}/count`);
  }

}
