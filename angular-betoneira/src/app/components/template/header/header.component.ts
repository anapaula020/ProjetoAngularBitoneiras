import { Component } from '@angular/core';
import { Router,RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder,FormGroup,ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButton,MatButtonModule,MatIconButton } from '@angular/material/button';
import { MatToolbar,MatToolbarModule } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { MatBadge } from '@angular/material/badge';
import { SidebarService } from '../../../services/sidebar.service';
import { AuthService } from '../../../services/auth.service';
import { Subscription } from 'rxjs';
import { Cliente } from '../../../models/cliente.model';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
    imports: [CommonModule, RouterModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatToolbarModule, MatToolbar, MatIcon, MatBadge, MatButton, MatIconButton, RouterModule]
})
export class HeaderComponent {
    searchForm: FormGroup;
    userRole: string | null = null;
    clienteLogado: Cliente | null = null;
    private subscription = new Subscription();

    constructor(private router: Router,private formBuilder: FormBuilder,private sidebarService: SidebarService,private authService: AuthService) {
        this.searchForm = this.formBuilder.group({
            query: ['']
        });
    }

    ngOnInit(): void {
        this.subscription.add(this.authService.getClienteLogado().subscribe(data => {
                this.clienteLogado = data;
                this.userRole = this.authService.getUserRole();
            }
        ));
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    logout() {
        this.authService.removeToken();
        this.authService.removeUser();
        this.router.navigateByUrl('/login');
    }

    navigateTo(route: string): void {
        this.router.navigate([route]);
    }

    onSearch(): void {
        const query = this.searchForm.get('query')?.value;
        if(query) {
            this.router.navigate(['/loja'],{ queryParams: { search: query } });
        }
    }

    clickMenu() {
        this.sidebarService.toggle();
    }
}