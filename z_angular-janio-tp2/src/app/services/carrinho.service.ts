import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { LocalStorageService } from './local-storage.service';
import { ItemCarrinho } from '../models/item-carrinho';

@Injectable({
  providedIn: 'root'
})
export class CarrinhoService {

  private carrinhoSubject = new BehaviorSubject<ItemCarrinho[]>([]);
  carrinho$ = this.carrinhoSubject.asObservable();

  constructor(private localStorageService: LocalStorageService) {
    const carrinhoArmazenado = localStorageService.getItem('carrinho') || [];
    this.carrinhoSubject.next(carrinhoArmazenado);
  }

  adicionar(plano: ItemCarrinho): void {
    const carrinhoAtual = this.carrinhoSubject.value;
    const itemExistente = carrinhoAtual.find(item => item.id === plano.id);
    if (itemExistente) {                       
      itemExistente.quantidade = itemExistente.quantidade  + 1;
    } else {
      carrinhoAtual.push({... plano});
    }

    this.carrinhoSubject.next(carrinhoAtual);
    this.atualizarArmazenamentoLocal();
  }

  private atualizarArmazenamentoLocal() {
    this.localStorageService.setItem('carrinho', this.carrinhoSubject.value);
  }

  // resolver o problema da quantidade
  remover(item: ItemCarrinho): void {
    const carrinhoAtual = this.carrinhoSubject.value;
    const carrinhoAtualizado = carrinhoAtual.filter(itemCarrinho => itemCarrinho !== item);

    this.carrinhoSubject.next(carrinhoAtualizado);
    this.atualizarArmazenamentoLocal();
  }

  removerTudo(): void {
    this.localStorageService.removeItem('carrinho');
    window.location.reload(); // procuar algo melhor
  }

  obter(): ItemCarrinho[] {
    return this.carrinhoSubject.value;
  }

}