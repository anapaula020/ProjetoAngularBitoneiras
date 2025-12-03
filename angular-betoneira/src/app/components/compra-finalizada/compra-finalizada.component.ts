import { CommonModule,NgFor,NgIf } from '@angular/common';
import { Component,OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions,MatCardContent,MatCardFooter,MatCardModule,MatCardTitle } from '@angular/material/card';
import { Router } from '@angular/router';
import { CarrinhoService } from '../../services/carrinho.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { FooterComponent } from '../template/footer/footer.component';
import { HeaderComponent } from '../template/header/header.component';

@Component({
    selector: 'app-compra-finalizada',
    standalone: true,
    templateUrl: './compra-finalizada.component.html',
    styleUrls: ['./compra-finalizada.component.css'],
    imports: [NgIf,ReactiveFormsModule,CommonModule,MatCardModule,MatButtonModule,NgFor,MatCardActions,MatCardContent,MatCardTitle,MatCardFooter,HeaderComponent,FooterComponent]
})
export class CompraFinalizadaComponent implements OnInit {
    constructor(private carrinhoService: CarrinhoService,private localStorageService: LocalStorageService,private router: Router) {
    }

    ngOnInit(): void {
    }

    back() {
        this.router.navigateByUrl("");
    }
}