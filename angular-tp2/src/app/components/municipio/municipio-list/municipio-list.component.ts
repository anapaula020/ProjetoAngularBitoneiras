import { Component,OnInit } from '@angular/core';
import { Municipio } from '../../../models/municipio.model';
import { MunicipioService } from '../../../services/municipio.service';
import { NgFor } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-municipio-list',
    standalone: true,
    imports: [NgFor, MatToolbarModule, MatIconModule, MatButtonModule, MatTableModule, RouterModule],
    templateUrl: './municipio-list.component.html',
    styleUrl: './municipio-list.component.css'
})
export class MunicipioListComponent implements OnInit {
    municipios: Municipio[] = [];
    displayedColumns: string[] = ['id', 'nome', 'cidade', 'acao'];

    constructor(private municipioService: MunicipioService) {}

    ngOnInit(): void {
        this.municipioService.findAll().subscribe(data => { this.municipios = data });
    }
}