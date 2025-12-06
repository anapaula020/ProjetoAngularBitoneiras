import { Component, OnDestroy, OnInit } from '@angular/core';
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

@Component({
    selector: 'app-header-login',
    templateUrl: './header-login.component.html',
    styleUrls: ['./header-login.component.css'],
    imports: [CommonModule, RouterModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatToolbarModule, MatToolbar, MatIcon, MatBadge, MatButton, MatIconButton, RouterModule]
})
export class HeaderLoginComponent implements OnInit, OnDestroy {
    searchForm: FormGroup;
    constructor(private router: Router,private formBuilder: FormBuilder,private sidebarService: SidebarService) {
        this.searchForm = this.formBuilder.group({
            query: ['']
        });
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
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