import { CommonModule } from '@angular/common';
import { Component,OnInit, inject } from '@angular/core';
import { Router,RouterModule } from '@angular/router';
import { Fabricante } from '../../../models/fabricante.model';
import { FabricanteService } from '../../../services/fabricante.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule,PageEvent } from '@angular/material/paginator';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-fabricante-betoneira-list',
    templateUrl: './fabricante-list.component.html',
    styleUrls: ['./fabricante-list.component.css'],
    imports: [MatPaginatorModule, CommonModule, RouterModule, MatTableModule, MatButtonModule, MatCardModule, MatToolbarModule, HeaderAdminComponent, FooterAdminComponent, MatIconModule]
})
export class FabricanteListComponent implements OnInit {
    displayedColumns: string[] = ['id','nome','anoNascimento','nacionalidade','sexo','actions'];
    fabricantes: Fabricante[] = [];
    totalRecords = 0;
    pageSize = 10;
    page = 0;
    readonly dialog = inject(MatDialog);

    constructor(private fabricanteService: FabricanteService,private router: Router) { }

    ngOnInit(): void {
        this.loadFabricante();
    }

    paginar(event: PageEvent): void {
        this.page = event.pageIndex;
        this.pageSize = event.pageSize;
        this.ngOnInit();
    }

    loadFabricante(): void {
        this.fabricanteService.findAll(this.page,this.pageSize).subscribe((data: Fabricante[]) => {
            this.fabricantes = data;
        });
        this.fabricanteService.count().subscribe(data => { this.totalRecords = data });
    }

    editFabricante(id: number): void {
        this.router.navigate(['admin/fabricante/edit',id]);
    }

    deleteFabricante(id: number): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                this.fabricanteService.delete(id).subscribe(() => {
                    this.loadFabricante();
                });
            }
        });
    }
}