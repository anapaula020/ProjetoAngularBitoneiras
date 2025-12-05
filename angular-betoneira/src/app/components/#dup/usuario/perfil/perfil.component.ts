import { Component,OnInit } from '@angular/core';
import { UsuarioService } from '../../../services/usuario.service';
import { AuthService } from '../../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../../models/usuario.model';
import { CommonModule } from '@angular/common';
import { Endereco } from '../../../models/endereco.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
    selector: 'app-perfil',
    templateUrl: './perfil.component.html',
    styleUrls: ['./perfil.component.css'],
    imports: [FormsModule,CommonModule],
    standalone: true
})
export class PerfilComponent implements OnInit {
    usuario: Usuario | null = null;
    novaSenha: string = '';
    confimacao: string = '';
    senhaAntiga: string = '';
    newEmail: string = '';
    rua: string = '';
    numero: string = '';
    cep: string = '';
    cidade: string = '';
    estado: string = '';
    message: string | null = null;
    endereco: Endereco | null = null;

    constructor(
        private usuarioService: UsuarioService,
        private authService: AuthService,
        private snackBar: MatSnackBar,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.authService.getUsuarioLogado().subscribe({
            next: (usuario) => {
                if(usuario) {
                    this.usuario = usuario;
                }
            },
            error: (err) => {
                console.error('Erro ao carregar usuário:',err);
                this.showMessage('Erro ao carregar os dados do usuário.');
            }
        });
    }

    pedidos() {
        this.router.navigateByUrl('/meuspedidos');
    }

    changePassword() {
        if(this.novaSenha !== this.confimacao) {
            this.showMessage('As senhas não coincidem!');
            return;
        }

        this.usuarioService.updateSenha(this.senhaAntiga,this.novaSenha,this.confimacao).subscribe({
            next: (response) => {
                this.showMessage('Senha alterada com sucesso!');
            },
            error: (err) => {
                this.showMessage('Erro ao alterar a senha.');
                console.error('Erro ao alterar senha',err);
            }
        });
    }

    changeEmail() {
        if(!this.newEmail) {
            this.showMessage('Por favor, insira um email válido.');
            return;
        }

        this.usuarioService.updateEmail(this.newEmail).subscribe({
            next: (response) => {
                this.showMessage('Email alterado com sucesso!');
            },
            error: (err) => {
                this.showMessage('Erro ao alterar o email.');
            }
        });
    }

    changeAddress() {
        if(!this.rua || !this.numero || !this.cep || !this.cidade || !this.estado) {
            this.showMessage('Por favor, preencha todos os campos do endereço.');
            return;
        }

        this.usuarioService.updateEndereco(this.rua,this.numero,this.cep,this.cidade,this.estado).subscribe({
            next: (response) => {
                this.showMessage('Endereço alterado com sucesso!');
            },
            error: (err) => {
                this.showMessage('Erro ao alterar o endereço.');
            }
        });
    }

    private showMessage(message: string) {
        this.snackBar.open(message,'Fechar',{
            duration: 3000,
        });
    }
}