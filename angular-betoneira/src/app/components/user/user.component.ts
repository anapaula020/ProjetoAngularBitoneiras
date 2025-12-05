import { Component, OnInit } from "@angular/core";
import { UserService } from "../../services/user.service";
import { Usuario } from "../../models/usuario.model";

@Component({
    selector: "app-user",
    templateUrl: "./user.component.html",
    styleUrls: ["./user.component.css"]
})
export class UserComponent implements OnInit {

    user: Usuario | null = null;
    loading = true;
    errorMessage = "";

    constructor(private userService: UserService) {}

    ngOnInit(): void {
        this.loadUser();
    }

    loadUser(): void {
        this.userService.getMyUserInfo().subscribe({
            next: (data) => {
                this.user = data;
                this.loading = false;
            },
            error: (err) => {
                this.errorMessage = "Erro ao carregar informações do usuário";
                this.loading = false;
            }
        });
    }
}