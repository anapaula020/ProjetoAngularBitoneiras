import { CommonModule } from '@angular/common';
import { Component,OnInit, inject } from '@angular/core';
import { Router,RouterModule } from '@angular/router';
import { ItemPedido } from '../../../models/itemPedido.model';
import { EscritorNovelService } from '../../../services/endereco.service';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { MatPaginatorModule,PageEvent } from '@angular/material/paginator';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-escritor-novel-list',
    standalone: true,
    templateUrl: './escritor-list.component.html',
    styleUrls: ['./escritor-list.component.css'],
    imports: [MatPaginatorModule,CommonModule,RouterModule,MatTableModule,MatButtonModule,MatCardModule,MatToolbarModule,HeaderAdminComponent,FooterAdminComponent]
})
export class ItemPedidoListComponent implements OnInit {
    displayedColumns: string[] = ['id','nome','anoNascimento','nacionalidade','sexo','actions'];
    escritores: ItemPedido[] = [];
    totalRecords = 0;
    pageSize = 10;
    page = 0;
    readonly dialog = inject(MatDialog);

    constructor(private escritorService: EscritorNovelService,private router: Router) { }

    ngOnInit(): void {
        this.loadEscritores();
    }

    paginar(event: PageEvent): void {
        this.page = event.pageIndex;
        this.pageSize = event.pageSize;
        this.ngOnInit();
    }

    loadEscritores(): void {
        this.escritorService.findAll(this.page,this.pageSize).subscribe((data: ItemPedido[]) => {
            this.escritores = data;
        });
        this.escritorService.count().subscribe(data => { this.totalRecords = data });
    }

    editEscritor(id: number): void {
        this.router.navigate(['admin/escritor/edit',id]);
    }

    deleteEscritor(id: number): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                this.escritorService.delete(id).subscribe(() => {
                    this.loadEscritores();
                });
            }
        });
    }
}