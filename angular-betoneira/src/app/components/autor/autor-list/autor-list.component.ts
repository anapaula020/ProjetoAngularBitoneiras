import { CommonModule } from '@angular/common';
import { Component,OnInit, inject } from '@angular/core';
import { Router,RouterModule } from '@angular/router';
import { Fabricante } from '../../../models/fabricante';
import { AutorService } from '../../../services/fabricante.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { MatIconModule } from '@angular/material/icon';
import { Novel } from '../../../models/novel.model';
import { MatPaginatorModule,PageEvent } from '@angular/material/paginator';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-autor-betoneira-list',
    standalone: true,
    templateUrl: './autor-list.component.html',
    styleUrls: ['./autor-list.component.css'],
    imports: [MatPaginatorModule,CommonModule,RouterModule,MatTableModule,MatButtonModule,MatCardModule,MatToolbarModule,HeaderAdminComponent,FooterAdminComponent,MatIconModule]
})
export class AutorListComponent implements OnInit {
    displayedColumns: string[] = ['id','nome','anoNascimento','nacionalidade','sexo','actions'];
    autores: Fabricante[] = [];
    totalRecords = 0;
    pageSize = 10;
    page = 0;
    readonly dialog = inject(MatDialog);

    constructor(private autorService: AutorService,private router: Router) { }

    ngOnInit(): void {
        this.loadAutores();
    }

    paginar(event: PageEvent): void {
        this.page = event.pageIndex;
        this.pageSize = event.pageSize;
        this.ngOnInit();
    }

    loadAutores(): void {
        this.autorService.findAll(this.page,this.pageSize).subscribe((data: Fabricante[]) => {
            this.autores = data;
        });
        this.autorService.count().subscribe(data => { this.totalRecords = data });
    }

    editAutor(id: number): void {
        this.router.navigate(['admin/autor/edit',id]);
    }

    deleteAutor(id: number): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                this.autorService.delete(id).subscribe(() => {
                    this.loadAutores();
                });
            }
        });
    }
}