import { CommonModule } from '@angular/common';
import { Component,OnInit,signal } from '@angular/core';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { Betoneira } from '../../../models/betoneira.model';
import { BetoneiraService } from '../../../services/betoneira.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderComponent } from "../../template/header/header.component";
import { FooterComponent } from "../../template/footer/footer.component";
import { MatPaginatorModule } from '@angular/material/paginator';
import { CarrinhoService } from '../../../services/carrinho.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BetoneiraCardListComponent } from "../betoneira-card-list/betoneira-card-list.component";

type Card = {
    id: number;
    nome: string;
    preco: number;
    imageUrl: string;
}

@Component({
    selector: 'app-betoneira-info',
    standalone: true,
    templateUrl: './betoneira-info.component.html',
    styleUrls: ['./betoneira-info.component.css'],
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
        BetoneiraCardListComponent
    ]
})
export class BetoneiraInfoComponent implements OnInit {
    betoneira!: Betoneira;
    otherBetoneiras: Betoneira[] = [];
    cards = signal<Card[]>([]);

    constructor(
        private betoneiraService: BetoneiraService,
        private router: Router,
        private carrinhoService: CarrinhoService,
        private snackBar: MatSnackBar,
        private route: ActivatedRoute
    ) { }

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            const betoneiraId = parseInt(params.get('id') ?? '-1',10);
            if(betoneiraId !== -1) {
                this.betoneiraService.findById(betoneiraId).subscribe((data: Betoneira) => {
                    this.betoneira = data;
                    this.betoneira.imageUrl = this.betoneiraService.toImageUrl(this.betoneira.imageUrl);
                    // this.loadOtherBetoneiras();
                });
            }
        });
    }

    // loadOtherBetoneiras(): void {
    //     // this.betoneiraService.findByGenre(this.betoneira.genero.id).subscribe(data => {
    //     this.betoneiraService.subscribe(data => {
    //         this.otherBetoneiras = data.filter(m => m.id !== this.betoneira.id);
    //         this.otherBetoneiras.forEach(betoneira => {
    //             if(!betoneira.imageUrl.startsWith('http')) {
    //                 betoneira.imageUrl = 'http://localhost:8000/betoneira/image/download/' + betoneira.imageUrl;
    //             }
    //         });
    //         this.carregarCards();
    //     });
    // }

    carregarCards() {
        const cards: Card[] = [];
        this.otherBetoneiras.forEach(otherBetoneira => {
            if(otherBetoneira.id === this.betoneira.id) return;
            cards.push({
                id: otherBetoneira.id,
                nome: otherBetoneira.nome,
                preco: otherBetoneira.preco,
                imageUrl: this.betoneiraService.toImageUrl(otherBetoneira.imageUrl)
            });
        });
        this.cards.set(cards);

    }

    verBetoneira(id: number): void {
        this.router.navigateByUrl('loja/betoneira/' + id);
    }

    adicionarAoCarrinho(betoneira: Betoneira): void {
        this.showSnackbarTopPosition('Produto adicionado no carrinho.');
        this.carrinhoService.adicionar({
            type: 1,
            id: betoneira.id,
            nome: betoneira.nome,
            imageUrl: betoneira.imageUrl ?? "livro.jpg", 
            preco: betoneira.preco,
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
