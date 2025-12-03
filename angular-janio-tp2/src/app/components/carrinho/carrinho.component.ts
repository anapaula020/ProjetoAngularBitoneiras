import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CarrinhoService } from '../../services/carrinho.service';
import { ItemCarrinho } from '../../models/item-carrinho';

@Component({
  selector: 'app-carrinho',
  imports: [NgIf, NgFor],
  templateUrl: './carrinho.component.html',
  styleUrl: './carrinho.component.css'
})
export class CarrinhoComponent implements OnInit {

  carrinhoItens: ItemCarrinho[] = [];

  constructor(private carrinhoService: CarrinhoService,
              private router: Router) {}

  ngOnInit(): void {
    this.carrinhoService.carrinho$.subscribe(itens => {
      this.carrinhoItens = itens;
    });
  }

  removerItem(item: ItemCarrinho): void {
    this.carrinhoService.remover(item);
  }
  
  calcularTotal(): number {
    return this.carrinhoItens.reduce((total, item) => total + (item.quantidade ?? 0) * (item.preco?? 0), 0);
  }

  finalizarCompra() {
    // boa sorte!!!
  }

  

}
