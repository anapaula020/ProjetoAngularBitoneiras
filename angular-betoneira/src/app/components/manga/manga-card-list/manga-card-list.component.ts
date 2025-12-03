import { Component, OnInit, signal } from '@angular/core';
import { Manga } from '../../../models/manga.model';
import { MangaService } from '../../../services/manga.service';
import { MatCardActions, MatCardContent, MatCardFooter, MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { NgForOf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormBuilder, FormGroup, FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CarrinhoService } from '../../../services/carrinho.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { GeneroManga } from '../../../models/generoManga.model';
import { MatPaginator } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { AutorManga } from '../../../models/autorManga.model';
import { AutorService } from '../../../services/autorManga.service';

type Card = {
    id: number;
    nome: string;
    sinopse: string;
    lancamento: number;
    preco: number;
    imageUrl: string;
    genero: GeneroManga;
}

@Component({
    imports: [FormsModule,MatSelectModule,MatCardModule, MatButtonModule, MatFormFieldModule, NgForOf, MatCardActions, MatCardContent, MatCardTitle, MatCardFooter, CommonModule, MatPaginator],
    standalone: true,
    templateUrl: './manga-card-list.component.html',
    styleUrls: ['./manga-card-list.component.css']
})
export class MangaCardListComponent implements OnInit {
    mangas: Manga[] = [];
    cards = signal<Card[]>([]);
    searchForm: FormGroup;
    selectedGenero: number | null = null;
    generos: GeneroManga[] = [];
    precos: any[] = [];
    autores: AutorManga[] = [];
    selectedAutor: number | null = null;
    selectedPreco: number | null = null;
    totalMangas = 0;
    pageSize = 30;
    mostrarFiltros = false;
    
    pageSizeOptions = [10, 30, 60, 90, 110, 130];
    currentPage = 0;          constructor(
        private route: ActivatedRoute,
        private router: Router,
        private mangaService: MangaService,
        private formBuilder: FormBuilder,
        private autorService: AutorService,
        private carrinhoService: CarrinhoService,
        private snackBar: MatSnackBar
    ) {
        this.searchForm = this.formBuilder.group({
            query: ['']
        });
    }

    ngOnInit(): void {
        this.autorService.findAll().subscribe((data) => {
            this.autores = data;
        });
        this.mangaService.findGeneros().subscribe((data) => {
            this.generos = data;
        });
        this.mangaService.count().subscribe(total => {
            this.totalMangas = total;
            this.loadMangas();
        });
        this.route.queryParams.subscribe(params => {
            if (!params["search"]) return;
            this.mangaService.findByName(params["search"]).subscribe(data => {
                this.mangas = data;
                this.carregarCards();
            });
        });
        this.precos = [
            { id: 1, nome: 'Menor que 20', min: 0, max: 20 },
            { id: 2, nome: 'Entre 20 e 30', min: 20, max: 30 },
            { id: 3, nome: 'Entre 30 e 40', min: 30, max: 40 },
            { id: 4, nome: 'Entre 40 e 50', min: 40, max: 50 },
            { id: 5, nome: 'Entre 50 e 60', min: 50, max: 60 },
            { id: 6, nome: 'Entre 60 e 80', min: 50, max: 80 },
            { id: 7, nome: 'Maior que 80', min: 50, max: Infinity }
        ];
    }


    toggleFiltros() {
        this.mostrarFiltros = !this.mostrarFiltros;
    }
    
    filtrarPreco(id: number) {
        const selectedPrice = this.precos.find(preco => preco.id === id);
        if (selectedPrice) {
            this.mangaService.findByPrice(selectedPrice.min, selectedPrice.max).subscribe(data => {
                this.mangas = data;
                this.carregarCards();
            });
        }
    }

    filtrarAutor(id: number) {
        this.mangaService.findByAuthor(id).subscribe(data => {
            this.mangas = data;
            this.carregarCards();
        });
    }

    filtrarGenero(id: number) {
        this.mangaService.findByGenre(id).subscribe(data => {
            this.mangas = data;
            this.carregarCards();
        });
    }

    loadMangas(page: number = 0) {
        this.mangaService.findAll(page, this.pageSize).subscribe(data => {
            this.mangas = data;
            this.carregarCards();
        });
    }

    verManga(id: number) {
        this.router.navigateByUrl('loja/manga/' + id);
    }

    carregarCards() {
        const cards: Card[] = [];
        this.mangas.forEach(manga => {
            cards.push({
                id: manga.id,
                nome: manga.nome,
                sinopse: manga.sinopse,
                lancamento: manga.lancamento,
                preco: manga.preco,
                genero: manga.genero,
                imageUrl: this.mangaService.toImageUrl(manga.imageUrl)
            });
        });
        this.cards.set(cards);
    }

    adicionarAoCarrinho(card: Card) {
        this.showSnackbarTopPosition('Produto adicionado ao carrinho.');
        this.carrinhoService.adicionar({
            type: 1,
            id: card.id,
            nome: card.nome,
            imageUrl: card.imageUrl ?? "livro.jpg", 
            preco: card.preco,
            quantidade: 1
        });
    }

    showSnackbarTopPosition(content: any) {
        this.snackBar.open(content, 'fechar', {
            duration: 3000,
            verticalPosition: "top",
            horizontalPosition: "center"
        });
    }

    onPageChange(event: any) {
        this.currentPage = event.pageIndex;
        this.pageSize = event.pageSize;
        this.loadMangas(this.currentPage);
    }
}
