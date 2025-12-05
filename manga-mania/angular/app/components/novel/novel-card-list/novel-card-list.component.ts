import { NgFor } from '@angular/common';
import { Component,OnInit,signal } from '@angular/core';
import { FormBuilder,FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions,MatCardContent,MatCardFooter,MatCardModule,MatCardTitle } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute,Router } from '@angular/router';
import { Novel } from '../../../models/novel.model';
import { CarrinhoService } from '../../../services/carrinho.service';
import { NovelService } from '../../../services/novel.service';

type Card = {
    id: number,
    nome: string,
    sinopse: string,
    lancamento: number,
    preco: number,
    capitulos: number
    imageUrl: string;
}

@Component({
    selector: 'app-novel-card-list',
    standalone: true,
    imports: [MatCardModule,MatButtonModule,NgFor,MatCardActions,MatCardContent,MatCardTitle,MatCardFooter],
    templateUrl: './novel-card-list.component.html',
    styleUrl: './novel-card-list.component.css'
})
export class NovelCardListComponent implements OnInit {
    novels: Novel[] = [];
    cards = signal<Card[]>([]);
    searchForm: FormGroup;
    pageSize = 30;
    pageSizeOptions = [10, 30, 60, 90, 110, 130];

    constructor(private route: ActivatedRoute,private router: Router,private novelService: NovelService,private formBuilder: FormBuilder,private carrinhoService: CarrinhoService,private snackBar: MatSnackBar) {
        this.searchForm = this.formBuilder.group({
            query: ['']
        });
    }

    ngOnInit(): void {
        this.loadNovels();
    }

    loadNovels() {
        this.novelService.findAll(0,10).subscribe(data => {
            this.novels = data;
            this.carregarCards();
        })
    }

    carregarCards() {
        const cards: Card[] = [];
        this.novels.forEach(novel => {
            cards.push({
                id: novel.id,
                nome: novel.nome,
                sinopse: novel.sinopse,
                lancamento: novel.lancamento,
                preco: novel.preco,
                capitulos: novel.capitulos,
                imageUrl: this.novelService.toImageUrl(novel.id, novel.imageUrl)
            })
        });
        this.cards.set(cards);
    }

    adicionarAoCarrinho(card: Card) {
        this.showSnackbarTopPosition('Produto adicionado ao carrinho');
        this.carrinhoService.adicionar({
            type: 2, 
            id: card.id,
            nome: card.nome,
            imageUrl: card.imageUrl ?? "livro.jpg", 
            preco: card.preco,
            quantidade: 1
        });
    }

    showSnackbarTopPosition(content: any) {
        this.snackBar.open(content,'fechar',{
            duration: 3000,
            verticalPosition: "top",
            horizontalPosition: "center"
        });
    }
}