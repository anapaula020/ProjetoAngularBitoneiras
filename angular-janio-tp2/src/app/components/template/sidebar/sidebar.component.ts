import { Component, OnInit, ViewChild } from '@angular/core';
import { MatListItem, MatNavList } from '@angular/material/list';
import { MatDrawer, MatDrawerContent, MatDrawerContainer } from '@angular/material/sidenav';
import { RouterLink, RouterOutlet } from '@angular/router';
import { SidebarService } from '../../../services/sidebar-service';
import { MatToolbar } from "@angular/material/toolbar";

@Component({
  selector: 'app-sidebar',
  imports: [MatNavList, MatListItem, RouterLink, MatDrawerContainer, MatDrawer, MatToolbar, MatDrawerContent, RouterOutlet],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {

  @ViewChild("drawer") public drawer!: MatDrawer;

  constructor(private sidebarService: SidebarService) {

  }

  ngOnInit(): void {
    this.sidebarService.sideNavToggleSubject.subscribe(
      () => { 
        this.drawer?.toggle();
      }
    )
  }

}
