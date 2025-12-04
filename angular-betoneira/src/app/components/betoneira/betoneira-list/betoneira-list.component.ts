import { CommonModule } from '@angular/common';
import { Component,OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule,PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router,RouterModule } from '@angular/router';
import { Manga } from '../../../models/manga.model';
import { CarrinhoService } from '../../../services/carrinho.service';
import { MangaService } from '../../../services/manga.service';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-manga-list',
    standalone: true,
    templateUrl: './manga-list.component.html',
    styleUrls: ['./manga-list.component.css'],
    imports: [MatPaginatorModule,CommonModule,RouterModule,MatTableModule,MatButtonModule,MatCardModule,MatToolbarModule,HeaderAdminComponent,FooterAdminComponent]
})
export class MangaListComponent implements OnInit {
    displayedColumns: string[] = ['id','nome','paginas','preco','sinopse','lancamento','estoque','color','idAutor','genero','actions'];
    mangas: Manga[] = [];
    totalRecords = 0;
    pageSize = 10;
    page = 0;
    readonly dialog = inject(MatDialog);

    constructor(private mangaService: MangaService,private router: Router,private carrinhoService: CarrinhoService,private snackBar: MatSnackBar) { }

    ngOnInit(): void {
        this.loadMangas();
    }

    showSnackBarTopPosition(content: string) {
        this.snackBar.open(content,'fechar',{
            duration: 3000,
            verticalPosition: "top",
            horizontalPosition: "center"
        })
    };

    paginar(event: PageEvent): void {
        this.page = event.pageIndex;
        this.pageSize = event.pageSize;
        this.ngOnInit();
    }

    loadMangas(): void {
        this.mangaService.findAll(this.page,this.pageSize).subscribe((data: Manga[]) => {
            this.mangas = data;
        });
        this.mangaService.count().subscribe(data => { this.totalRecords = data });
    }

    editManga(id: number): void {
        this.router.navigate(['admin/manga/edit',id]);
    }

    deleteManga(id: number): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                this.mangaService.delete(id).subscribe(() => {
                    this.loadMangas();
                });
            }
        });
    }
}