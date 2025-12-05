import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ItemCarrinho } from '../models/itemCarrinho.model';
import { LocalStorageService } from './local-storage.service';

@Injectable({
    providedIn: 'root'
})

export class CarrinhoService {
    private carrinhoSubject = new BehaviorSubject<ItemCarrinho[]>([]);
    carrinhos: Observable<ItemCarrinho[]> = this.carrinhoSubject;

    constructor(private localStorage: LocalStorageService) {
        this.carrinhoSubject.next(localStorage.getItem('carrinho') || []);
    }

    obter(): ItemCarrinho[] {
        return this.carrinhoSubject.value;
    }

    adicionar(itemCarrinho: ItemCarrinho): void {
        const carrinhoAtual = this.carrinhoSubject.value;
        const itemExistente = carrinhoAtual.find(item => item.id === itemCarrinho.id);

        if(itemExistente) {
            itemExistente.quantidade += itemCarrinho.quantidade || 1;
        } else {
            carrinhoAtual.push(itemCarrinho);
        }

        this.carrinhoSubject.next(carrinhoAtual);
    }

    removerTudo(): void {
        this.localStorage.removeItem('carrinho');
        window.location.reload();
    }

    diminuirItem(itemCarrinho: ItemCarrinho): void {
        const itemExistente = this.carrinhoSubject.value.find(item => item.id === itemCarrinho.id);
        if(itemExistente){
            itemExistente.quantidade -= 1;
            if(itemExistente.quantidade <=0){
                this.removerItem(itemCarrinho);
            }
            if(itemExistente.quantidade <= 0) this.carrinhoSubject.value.filter(item => item.id !== itemCarrinho.id);
        };
        this.carrinhoSubject.next(this.carrinhoSubject.value);
        this.atualizar();
    }

    removerItem(itemCarrinho: ItemCarrinho): void {
        this.carrinhoSubject.next(this.carrinhoSubject.value.filter(item => item.id !== itemCarrinho.id));
        this.atualizar();
    }

    private atualizar(): void {
        this.localStorage.setItem('carrinho',this.carrinhoSubject.value);
    }
}
