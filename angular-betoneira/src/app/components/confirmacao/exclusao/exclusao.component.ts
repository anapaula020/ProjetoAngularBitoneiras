import { CommonModule,NgFor,NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions,MatCardContent,MatCardFooter,MatCardModule,MatCardTitle } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
    selector: 'app-exclusao',
    templateUrl: './exclusao.component.html',
    styleUrls: ['./exclusao.component.css'],
    imports: [NgIf, MatDialogModule, CommonModule, MatCardModule, MatButtonModule, NgFor, MatCardActions, MatCardContent, MatCardTitle, MatCardFooter]
})
export class ExclusaoComponent {
    constructor() {
    }
}