import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Regiao } from '../models/regiao.model';

@Injectable({
  providedIn: 'root'
})
export class RegiaoService {
  private baseUrl = 'http://localhost:8080/regioes';

  constructor(private httpClient: HttpClient) { }

  getRegioes(): Observable<Regiao[]> {
    return this.httpClient.get<Regiao[]>(this.baseUrl);
  }

  buscarPorId(id: string): Observable<Regiao> {
    return this.httpClient.get<Regiao>(`${this.baseUrl}/${id}`);
  }  
}
