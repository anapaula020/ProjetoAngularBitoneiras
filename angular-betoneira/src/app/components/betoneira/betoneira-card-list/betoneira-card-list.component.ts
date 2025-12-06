import { Component,OnInit,signal } from '@angular/core';
import { Betoneira } from '../../../models/betoneira.model';
import { BetoneiraService } from '../../../services/betoneira.service';
import { MatCardActions,MatCardContent,MatCardFooter,MatCardModule,MatCardTitle } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { NgForOf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormBuilder,FormGroup,FormsModule } from '@angular/forms';
import { ActivatedRoute,Router } from '@angular/router';
import { CarrinhoService } from '../../../services/carrinho.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatPaginator } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { Fabricante } from '../../../models/fabricante.model';
import { FabricanteService } from '../../../services/fabricante.service';

type Card = {
    id: number,
    nome: string,
    descricao: string,
    preco: number,
    quantidadeEstoque: number,
    modelo: string,
    marca: string,
    capacidade: number,
    tipo: string, 
    idFabricante?: Fabricante | null, 
    imageUrl: string
}

@Component({
    imports: [FormsModule,MatSelectModule,MatCardModule,MatButtonModule,MatFormFieldModule,NgForOf,MatCardActions,MatCardContent,MatCardTitle,MatCardFooter,CommonModule,MatPaginator],
    standalone: true,
    templateUrl: './betoneira-card-list.component.html',
    styleUrls: ['./betoneira-card-list.component.css']
})
export class BetoneiraCardListComponent implements OnInit {
    betoneiras: Betoneira[] = [];
    cards = signal<Card[]>([]);
    searchForm: FormGroup;
    selectedTipo: string | null = null;
    selectedFabricante: number | null = null;
    precos: any[] = [];
    fabricantes: Fabricante[] = [];
    selectedFornecedor: number | null = null;
    selectedPreco: number | null = null;
    totalBetoneiras = 0;
    pageSize = 30;
    mostrarFiltros = false;

    pageSizeOptions = [10,30,60,90,110,130];
    currentPage = 0; constructor(
        private route: ActivatedRoute,
        private router: Router,
        private betoneiraService: BetoneiraService,
        private formBuilder: FormBuilder,
        private fabricanteService: FabricanteService,
        private carrinhoService: CarrinhoService,
        private snackBar: MatSnackBar
    ) {
        this.searchForm = this.formBuilder.group({
            query: ['']
        });
    }

    ngOnInit(): void {
        this.fabricanteService.findAll().subscribe((data) => {
            this.fabricantes = data;
        });
        this.betoneiraService.count().subscribe(total => {
            this.totalBetoneiras = total;
            this.loadBetoneiras();
        });
        this.route.queryParams.subscribe(params => {
            if(!params["search"]) return;
            this.betoneiraService.findByName(params["search"]).subscribe(data => {
                this.betoneiras = data;
                this.carregarCards();
            });
        });
        this.precos = [
            { id: 1,nome: 'Menor que 20',min: 0,max: 20 },
            { id: 2,nome: 'Entre 20 e 30',min: 20,max: 30 },
            { id: 3,nome: 'Entre 30 e 40',min: 30,max: 40 },
            { id: 4,nome: 'Entre 40 e 50',min: 40,max: 50 },
            { id: 5,nome: 'Entre 50 e 60',min: 50,max: 60 },
            { id: 6,nome: 'Entre 60 e 80',min: 50,max: 80 },
            { id: 7,nome: 'Maior que 80',min: 50,max: Infinity }
        ];
    }


    toggleFiltros() {
        this.mostrarFiltros = !this.mostrarFiltros;
    }

    filtrarTipo(nome: string) {
        this.betoneiraService.findByTipo(nome).subscribe(data => {
            this.betoneiras = data;
            this.carregarCards();
        });
    }

    filtrarPreco(id: number) {
        const selectedPrice = this.precos.find(preco => preco.id === id);
        if(selectedPrice) {
            this.betoneiraService.findByPrice(selectedPrice.min,selectedPrice.max).subscribe(data => {
                this.betoneiras = data;
                this.carregarCards();
            });
        }
    }

    filtrarFabricante(id: number) {
        this.betoneiraService.findByFabricante(id).subscribe(data => {
            this.betoneiras = data;
            this.carregarCards();
        });
    }

    loadBetoneiras(page: number = 0) {
        this.betoneiraService.findAll(page,this.pageSize).subscribe(data => {
            this.betoneiras = data;
            this.carregarCards();
        });
    }

    verBetoneira(id: number) {
        this.router.navigateByUrl('loja/betoneira/' + id);
    }

    carregarCards() {
        const cards: Card[] = [];
        this.betoneiras.forEach(betoneira => {
            cards.push({
                id: betoneira.id,
                nome: betoneira.nome,
                descricao: betoneira.descricao,
                preco: betoneira.preco,
                quantidadeEstoque: betoneira.quantidadeEstoque,
                modelo: betoneira.modelo,
                marca: betoneira.marca,
                capacidade: betoneira.capacidade,
                tipo: betoneira.tipo, 
                idFabricante: betoneira.idFabricante, 
                imageUrl: this.betoneiraService.toImageUrl(betoneira.imageUrl)
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
            imageUrl: card.imageUrl ?? "betoneira.jpg",
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

    onPageChange(event: any) {
        this.currentPage = event.pageIndex;
        this.pageSize = event.pageSize;
        this.loadBetoneiras(this.currentPage);
    }
}
