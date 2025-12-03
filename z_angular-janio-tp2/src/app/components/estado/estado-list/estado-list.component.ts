import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Estado } from '../../../models/estado.model';
import { EstadoService } from '../../../services/estado.service';
import { RouterLink } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-estado-list',
  imports: [MatTableModule, MatInputModule, MatFormFieldModule,
    MatToolbarModule, MatButtonModule, RouterLink, MatIcon, MatPaginatorModule],
  templateUrl: './estado-list.component.html',
  styleUrl: './estado-list.component.css'
})
export class EstadoListComponent implements OnInit {
  displayedColumns: string[] = ['numero', 'nome', 'sigla', 'regiao', 'acao'];
  estados: Estado[] = [];
  dataSource = new MatTableDataSource(this.estados);
  // variaveis de controle de paginacao
  totalRecords = 0;
  page = 0;
  pageSize = 2;

  constructor(private estadoService: EstadoService) {

  }

  ngOnInit(): void {
    console.log(this.page);
    console.log(this.pageSize);
    this.estadoService.getEstados(this.page, this.pageSize).subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
    })

    this.estadoService.count().subscribe(data => {
      this.totalRecords = data;
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  paginar(event: PageEvent): void {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.ngOnInit();
  }

}
