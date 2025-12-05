import { CommonModule,NgFor,NgIf } from '@angular/common';
import { Component,OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions,MatCardContent,MatCardFooter,MatCardModule,MatCardTitle } from '@angular/material/card';
import { Router } from '@angular/router';
import { HeaderComponent } from '../../template/header/header.component';
import { FooterComponent } from '../../template/footer/footer.component';

@Component({
    selector: 'app-desconhecido',
    standalone: true,
    templateUrl: './desconhecido.component.html',
    styleUrls: ['./desconhecido.component.css'],
    imports: [NgIf,ReactiveFormsModule,CommonModule,MatCardModule,MatButtonModule,NgFor,MatCardActions,MatCardContent,MatCardTitle,MatCardFooter,HeaderComponent,FooterComponent]
})
export class DesconhecidoComponent implements OnInit {
    constructor(private router: Router) {
    }

    ngOnInit(): void {
    }
}