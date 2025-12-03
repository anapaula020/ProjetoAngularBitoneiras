import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Aluno } from '../models/aluno.model';

@Injectable({
  providedIn: 'root'
})
export class AlunoService {
  private baseUrl = 'http://localhost:8080/alunos';

  constructor(private httpClient: HttpClient) { }

  getAlunos(page?: number, pageSize?: number): Observable<Aluno[]> {
    let params = {};

    if ((page !== undefined) && (pageSize !== undefined)) {
      
      params = {
        page: page.toString(),
        page_size: pageSize.toString()
      }
    }

    return this.httpClient.get<Aluno[]>(this.baseUrl, {params});
  }

  buscarPorId(id: string): Observable<Aluno> {
    return this.httpClient.get<Aluno>(`${this.baseUrl}/${id}`);
  }

  incluir(aluno: any): Observable<Aluno> {
    return this.httpClient.post<Aluno>(this.baseUrl, aluno);
  }

  alterar(aluno: any): Observable<any> {
    return this.httpClient.put<any>(`${this.baseUrl}/${aluno.id}`, aluno);
  }

  excluir(id: number): Observable<any> {
    return this.httpClient.delete<any>(`${this.baseUrl}/${id}`);
  }

  count(): Observable<number> {
    return this.httpClient.get<number>(`${this.baseUrl}/count`);
  }

}
