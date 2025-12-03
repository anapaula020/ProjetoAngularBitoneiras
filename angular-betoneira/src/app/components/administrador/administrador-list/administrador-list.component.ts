import { CommonModule } from '@angular/common';
import { Component,OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule,PageEvent } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router,RouterModule } from '@angular/router';
import { Administrador } from '../../../models/administrador.model';
import { AdministradorService } from '../../../services/administrador.service';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { FooterComponent } from "../../template/footer/footer.component";
import { HeaderComponent } from "../../template/header/header.component";
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-administrador-list',
    standalone: true,
    templateUrl: './administrador-list.component.html',
    styleUrls: ['./administrador-list.component.css'],
    imports: [MatPaginatorModule,CommonModule,RouterModule,MatTableModule,MatButtonModule,MatCardModule,MatToolbarModule,FooterComponent,HeaderComponent]
})
export class AdministradorListComponent implements OnInit {
    displayedColumns: string[] = ['id','username','email','cpf','actions'];
    administradores: Administrador[] = [];
    totalRecords = 0;
    pageSize = 10;
    page = 0;
    readonly dialog = inject(MatDialog);

    constructor(private administradorService: AdministradorService,private router: Router) { }

    ngOnInit(): void {
        this.loadAdministradores();
    }

    paginar(event: PageEvent): void {
        this.page = event.pageIndex;
        this.pageSize = event.pageSize;
        this.ngOnInit();
    }

    loadAdministradores(): void {
        this.administradorService.findAll(this.page,this.pageSize).subscribe((data: Administrador[]) => {
            this.administradores = data;
        });
        this.administradorService.count().subscribe(data => { this.totalRecords = data });
    }

    editAdministrador(id: number): void {
        this.router.navigate(['admin/administrador/edit',id]);
    }

    deleteAdministrador(id: number): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                this.administradorService.delete(id).subscribe(() => {
                    this.loadAdministradores();
                });
            };
        });
    }
}