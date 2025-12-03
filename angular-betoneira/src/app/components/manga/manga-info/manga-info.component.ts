import { CommonModule } from '@angular/common';
import { Component,OnInit,signal } from '@angular/core';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { Manga } from '../../../models/manga.model';
import { MangaService } from '../../../services/manga.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderComponent } from "../../template/header/header.component";
import { FooterComponent } from "../../template/footer/footer.component";
import { MatPaginatorModule } from '@angular/material/paginator';
import { CarrinhoService } from '../../../services/carrinho.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MangaCardListComponent } from "../manga-card-list/manga-card-list.component";

type Card = {
    id: number;
    nome: string;
    preco: number;
    imageUrl: string;
}

@Component({
    selector: 'app-manga-info',
    standalone: true,
    templateUrl: './manga-info.component.html',
    styleUrls: ['./manga-info.component.css'],
    imports: [
        CommonModule,
        RouterModule,
        MatTableModule,
        MatButtonModule,
        MatCardModule,
        MatToolbarModule,
        MatPaginatorModule,
        HeaderComponent,
        FooterComponent,
        MangaCardListComponent
    ]
})
export class MangaInfoComponent implements OnInit {
    manga!: Manga;
    otherMangas: Manga[] = [];
    cards = signal<Card[]>([]);

    constructor(
        private mangaService: MangaService,
        private router: Router,
        private carrinhoService: CarrinhoService,
        private snackBar: MatSnackBar,
        private route: ActivatedRoute
    ) { }

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            const mangaId = parseInt(params.get('id') ?? '-1',10);
            if(mangaId !== -1) {
                this.mangaService.findById(mangaId).subscribe((data: Manga) => {
                    this.manga = data;
                    this.manga.imageUrl = this.mangaService.toImageUrl(this.manga.imageUrl);
                    this.loadOtherMangas();
                });
            }
        });
    }

    loadOtherMangas(): void {
        this.mangaService.findByGenre(this.manga.genero.id).subscribe(data => {
            this.otherMangas = data.filter(m => m.id !== this.manga.id);
            this.otherMangas.forEach(manga => {
                if(!manga.imageUrl.startsWith('http')) {
                    manga.imageUrl = 'http://localhost:8000/manga/image/download/' + manga.imageUrl;
                }
            });
            this.carregarCards();
        });


    }

    carregarCards() {
        const cards: Card[] = [];
        this.otherMangas.forEach(otherManga => {
            if(otherManga.id === this.manga.id) return;
            cards.push({
                id: otherManga.id,
                nome: otherManga.nome,
                preco: otherManga.preco,
                imageUrl: this.mangaService.toImageUrl(otherManga.imageUrl)
            });
        });
        this.cards.set(cards);

    }

    verManga(id: number): void {
        this.router.navigateByUrl('loja/manga/' + id);
    }

    adicionarAoCarrinho(manga: Manga): void {
        this.showSnackbarTopPosition('Produto adicionado no carrinho.');
        this.carrinhoService.adicionar({
            type: 1,
            id: manga.id,
            nome: manga.nome,
            imageUrl: manga.imageUrl ?? "livro.jpg", 
            preco: manga.preco,
            quantidade: 1
        });
    }

    showSnackbarTopPosition(content: any): void {
        this.snackBar.open(content,'fechar',{
            duration: 3000,
            verticalPosition: "top",
            horizontalPosition: "center"
        });
    }
}
