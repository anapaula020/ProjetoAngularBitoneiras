import { CommonModule,NgFor,NgIf } from '@angular/common';
import { Component,OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions,MatCardContent,MatCardFooter,MatCardModule,MatCardTitle } from '@angular/material/card';
import { Router } from '@angular/router';
import { FooterComponent } from '../../template/footer/footer.component';
import { HeaderComponent } from '../../template/header/header.component';

@Component({
    selector: 'app-acessoproibido',
    standalone: true,
    templateUrl: './acessoproibido.component.html',
    styleUrls: ['./acessoproibido.component.css'],
    imports: [NgIf,ReactiveFormsModule,CommonModule,MatCardModule,MatButtonModule,NgFor,MatCardActions,MatCardContent,MatCardTitle,MatCardFooter,HeaderComponent,FooterComponent]
})
export class AcessoProibidoComponent implements OnInit {
    constructor(private router: Router) {
    }

    ngOnInit(): void {
    }
}