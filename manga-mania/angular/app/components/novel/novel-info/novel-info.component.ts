import { CommonModule } from '@angular/common';
import { Component,OnInit } from '@angular/core';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderComponent } from "../../template/header/header.component";
import { FooterComponent } from "../../template/footer/footer.component";
import { MatPaginatorModule,PageEvent } from '@angular/material/paginator';
import { Novel } from '../../../models/novel.model';
import { NovelService } from '../../../services/novel.service';
import { getGeneroNovelById } from '../../../models/generoNovel.model';
import { CarrinhoService } from '../../../services/carrinho.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Manga } from '../../../models/manga.model';

@Component({
    selector: 'app-novel-info',
    standalone: true,
    templateUrl: './novel-info.component.html',
    styleUrls: ['./novel-info.component.css'],
    imports: [MatPaginatorModule,CommonModule,RouterModule,MatTableModule,MatButtonModule,MatCardModule,MatToolbarModule,HeaderComponent,FooterComponent]
})
export class NovelInfoComponent implements OnInit {
    novel!: Novel;

    constructor(private novelService: NovelService,private router: Router,private carrinhoService: CarrinhoService,private snackBar: MatSnackBar) { }

    ngOnInit(): void {
        this.novelService.findById(parseInt(this.router.url.split("?")[0].split("/")?.pop() ?? "-1")).subscribe((data: Novel) => {
            this.novel = data;
        });
    }

    getGeneroString(id: number): string {
        try {
            return getGeneroNovelById(id);
        } catch(error) {
            console.error(error);
            return 'Gênero desconhecido';
        }
    }

    adicionarAoCarrinho(novel: any) {
        this.showSnackbarTopPosition('Produto adicionado no carinho.');
        this.carrinhoService.adicionar({
            type: 2, 
            id: novel.id,
            nome: novel.nome,
            imageUrl: novel.imageUrl ?? "livro.jpg", 
            preco: novel.preco,
            quantidade: 1
        })
    }

    showSnackbarTopPosition(content: any) {
        this.snackBar.open(content,'fechar',{
            duration: 3000,
            verticalPosition: "top",
            horizontalPosition: "center"
        });
    }
}