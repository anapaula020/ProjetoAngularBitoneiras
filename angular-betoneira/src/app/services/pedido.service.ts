import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pedido } from '../models/pedido.model';
import { ItemCarrinho } from '../models/item-carrinho';
import { Usuario } from '../models/usuario.model';
import { Endereco } from '../models/endereco.model';

@Injectable({
    providedIn: 'root'
})
export class PedidoService {
    private baseUrl = 'http://localhost:8000/pedidos';

    constructor(private httpClient: HttpClient) { }

    findAll(): Observable<Pedido[]> {
        return this.httpClient.get<Pedido[]>(this.baseUrl);
    }

    findMyPedidos(): Observable<Pedido[]>{
        return this.httpClient.get<Pedido[]>(`${this.baseUrl}/meus`)
    }

    count(): Observable<number> {
        return this.httpClient.get<number>(`${this.baseUrl}/count`);
    }

    findById(id: number): Observable<Pedido> {
        return this.httpClient.get<Pedido>(`${this.baseUrl}/${id}`);
    }

    insert(itens: ItemCarrinho[], endereco: Endereco): Observable<Pedido> {
        const data = {
            itens: itens.map(e => Object.assign({idManga: e.id, desconto: 0}, e)), endereco
        };
    console.log(data);

        return this.httpClient.post<Pedido>(this.baseUrl, data);
    }

    pagarPeloPix(id: number, cpf: string, valor: number): Observable<any> {
        const data = {
            idPedido: id,
            cpf,
            valor
        }
        return this.httpClient.patch(`${this.baseUrl}/pagar/pix`, data);
    }

    pagarPeloCredito(id: number,numero: number, nome: string,cvv:string,limite: number, parcelas: number): Observable<any> {
        const data ={
            idPedido: id,
            numero,
            nome,
            cvv,
            limite
        }
        return this.httpClient.patch(`${this.baseUrl}/pagar/credito/${parcelas}`, data);
    }

    pagarPeloDebito(id: number,numero: number, nome: string,cvv:string,limite: number): Observable<any> {
        const cartao = {
            idPedido: id,
            numero,
            nome,
            cvv,
            limite
        }
        return this.httpClient.patch(`${this.baseUrl}/pagar/debito`, cartao);
    }
}