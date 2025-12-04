import { CommonModule } from '@angular/common';
import { Component,OnInit,inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule,PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router,RouterModule } from '@angular/router';
import { Betoneira } from '../../../models/betoneira.model';
import { CarrinhoService } from '../../../services/carrinho.service';
import { BetoneiraService } from '../../../services/betoneira.service';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-betoneira-list',
    standalone: true,
    templateUrl: './betoneira-list.component.html',
    styleUrls: ['./betoneira-list.component.css'],
    imports: [MatPaginatorModule,CommonModule,RouterModule,MatTableModule,MatButtonModule,MatCardModule,MatToolbarModule,HeaderAdminComponent,FooterAdminComponent]
})
export class BetoneiraListComponent implements OnInit {
    displayedColumns: string[] = ['id','nome','paginas','preco','sinopse','lancamento','estoque','color','idAutor','genero','actions'];
    betoneiras: Betoneira[] = [];
    totalRecords = 0;
    pageSize = 10;
    page = 0;
    readonly dialog = inject(MatDialog);

    constructor(private betoneiraService: BetoneiraService,private router: Router,private carrinhoService: CarrinhoService,private snackBar: MatSnackBar) { }

    ngOnInit(): void {
        this.loadBetoneira();
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

    loadBetoneira(): void {
        this.betoneiraService.findAll(this.page,this.pageSize).subscribe((data: Betoneira[]) => {
            this.betoneiras = data;
        });
        this.betoneiraService.count().subscribe(data => { this.totalRecords = data });
    }

    editBetoneira(id: number): void {
        this.router.navigate(['admin/betoneira/edit',id]);
    }

    deleteBetoneira(id: number): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                this.betoneiraService.delete(id).subscribe(() => {
                    this.loadBetoneira();
                });
            }
        });
    }
}