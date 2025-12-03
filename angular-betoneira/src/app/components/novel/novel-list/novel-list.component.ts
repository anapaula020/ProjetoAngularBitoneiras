import { Component,OnInit } from '@angular/core';
import { Novel } from '../../../models/novel.model';
import { NovelService } from '../../../services/novel.service';
import { CommonModule,NgFor } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router,RouterModule } from '@angular/router';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule,PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
    selector: 'app-novel-list',
    standalone: true,
    imports: [MatPaginatorModule,CommonModule,RouterModule,MatTableModule,MatButtonModule,MatCardModule,MatToolbarModule,HeaderAdminComponent,FooterAdminComponent,NgFor,MatIconModule,HeaderAdminComponent,FooterAdminComponent],
    templateUrl: './novel-list.component.html',
    styleUrls: ['./novel-list.component.css']
})
export class NovelListComponent implements OnInit {
    displayedColumns: string[] = ['id','nome','genero','lancamento','preco','estoque','actions'];
    novels: Novel[] = [];
    totalRecords = 0;
    pageSize = 10;
    page = 0;

    constructor(private novelService: NovelService,private router: Router,private snackBar: MatSnackBar) { }

    ngOnInit(): void {
        this.loadNovels();
    }

    paginar(event: PageEvent): void {
        this.page = event.pageIndex;
        this.pageSize = event.pageSize;
        this.ngOnInit();
    }

    loadNovels(): void {
        this.novelService.findAll().subscribe((data: Novel[]) => {
            this.novels = data;
        });
        this.novelService.count().subscribe(data => { this.totalRecords = data });
    }

    editNovel(id: number): void {
        this.router.navigate(['admin/novel/edit',id]);
    }

    deleteNovel(id: number): void {
        this.novelService.delete(id).subscribe(() => {
            this.loadNovels();
        });
    }
}
