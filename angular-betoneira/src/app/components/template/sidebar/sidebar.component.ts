import { Component,OnInit,ViewChild } from '@angular/core';
import { MatDrawer,MatDrawerContainer,MatDrawerContent,MatSidenav } from '@angular/material/sidenav';
import { MatToolbar } from '@angular/material/toolbar';
import { MatList,MatListItem,MatNavList } from '@angular/material/list';
import { RouterModule,RouterOutlet } from '@angular/router';
import { SidebarService } from '../../../services/sidebar.service';
import {MatIconModule} from '@angular/material/icon';

@Component({
    selector: 'app-sidebar',
    imports: [MatIconModule, MatSidenav, MatDrawer, MatDrawerContainer, RouterModule, MatDrawerContent, MatToolbar, MatList, MatNavList, MatListItem, RouterOutlet],
    templateUrl: './sidebar.component.html',
    styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
    @ViewChild('drawer') public drawer!: MatDrawer;

    constructor(private sideBarService: SidebarService) { }
    showFiller = false;

    ngOnInit(): void {
        this.sideBarService.sideNavToggleSubject.subscribe(() => {
                this.drawer?.toggle();
            }
        )
    }
}