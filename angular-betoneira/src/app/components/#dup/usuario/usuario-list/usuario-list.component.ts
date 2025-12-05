import { CommonModule } from '@angular/common';
import { Component,OnInit, inject } from '@angular/core';
import { Router,RouterModule } from '@angular/router';
import { Usuario } from '../../../models/usuario.model';
import { UsuarioService } from '../../../services/usuario.service';
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
    selector: 'app-usuario-list',
    standalone: true,
    templateUrl: './usuario-list.component.html',
    styleUrls: ['./usuario-list.component.css'],
    imports: [MatPaginatorModule,CommonModule,RouterModule,MatTableModule,MatButtonModule,MatCardModule,MatToolbarModule,HeaderAdminComponent,FooterAdminComponent,MatIconModule]
})
export class UsuarioListComponent implements OnInit {
    displayedColumns: string[] = ['id','username','email','cpf','endereco','sexo','actions'];
    usuarios: Usuario[] = [];
    totalRecords = 0;
    pageSize = 10;
    page = 0;
    readonly dialog = inject(MatDialog);

    constructor(private usuarioService: UsuarioService,private router: Router) { }

    ngOnInit(): void {
        this.loadUsuarios();
    }

    paginar(event: PageEvent): void {
        this.page = event.pageIndex;
        this.pageSize = event.pageSize;
        this.ngOnInit();
    }

    loadUsuarios(): void {
        this.usuarioService.findAll(this.page,this.pageSize).subscribe((data: Usuario[]) => {
            this.usuarios = data;
        });
        this.usuarioService.count().subscribe(data => { this.totalRecords = data });
    }

    editUsuario(id: number): void {
        this.router.navigate(['admin/usuario/edit',id]);
    }

    deleteUsuario(id: number): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                this.usuarioService.delete(id).subscribe(() => {
                    this.loadUsuarios();
                });
            }
        });
    }
}