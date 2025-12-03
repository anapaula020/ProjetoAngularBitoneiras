import { CurrencyPipe, DecimalPipe, NgFor, NgIf } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Plano } from '../../../models/plano.model';
import { PlanoService } from '../../../services/plano-service';
import { CarrinhoService } from '../../../services/carrinho.service';

type CardPlano = {
  idPlano: number;
  titulo: string;
  preco: number;
  maxAlunos?: number|null;
  maxProfessores?: number|null;
  descontoAnual?: number|null;
  imagemUrl?: string;
};

@Component({
  selector: 'app-plano-card-list',
  standalone: true,
  imports: [MatCardModule, MatButton, CurrencyPipe],
  templateUrl: './plano-card-list.component.html',
  styleUrls: ['./plano-card-list.component.css']
})
export class PlanoCardListComponent implements OnInit {

  planos: Plano[] = [];
  cards = signal<CardPlano[]>([]);

  constructor(
    private planoService: PlanoService,
    private snackBar: MatSnackBar,
    private carrinhoService: CarrinhoService
  ) {}

  ngOnInit(): void {
    this.carregarPlanos();
  }

  carregarPlanos(): void {
    // ajuste os parâmetros de paginação conforme seu backend
    this.planoService.getPlanos(0, 10).subscribe({
      next: (data) => {
        this.planos = data;
        this.carregarCards();
      },
      error: () => this.showSnackbarTopPosition('Falha ao carregar planos.')
    });
  }

  carregarCards(): void {
    const list: CardPlano[] = this.planos.map(p => ({
      idPlano: p.id!,
      titulo: p.nome,
      preco: p.precoMensal,
      maxAlunos: p.maxAlunos,
      maxProfessores: p.maxProfessores,
      descontoAnual: p.descontoAnual,
      imagemUrl: p.nomeImagem ? this.planoService.getUrlImage(p.nomeImagem) : 'assets/img/planos/default.png'
    }));
    this.cards.set(list);
  }

  adicionarAoCarrinho(card: CardPlano): void {
    this.showSnackbarTopPosition('O Plano ('+ card.titulo + ') foi adicionado ao carrinho.');
    console.log('itemExistente: ');
    this.carrinhoService.adicionar({
      id: card.idPlano,
      nome: card.titulo,
      preco: card.preco,
      quantidade: 1
    });
  }

  showSnackbarTopPosition(content: string): void {
    this.snackBar.open(content, 'fechar', {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'center'
    });
  }
}
