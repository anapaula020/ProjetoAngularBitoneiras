import { CommonModule,NgFor,NgIf } from '@angular/common';
import { Component,OnInit,OnDestroy } from '@angular/core';
import { FormBuilder,ReactiveFormsModule,FormGroup,ValidationErrors } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions,MatCardContent,MatCardFooter,MatCardModule,MatCardTitle } from '@angular/material/card';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';
import { ItemCarrinho } from '../../models/itemCarrinho.model';
import { Usuario } from '../../models/usuario.model';
import { AuthService } from '../../services/auth.service';
import { CarrinhoService } from '../../services/carrinho.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { SidebarService } from '../../services/sidebar.service';
import { FooterComponent } from '../template/footer/footer.component';
import { HeaderComponent } from '../template/header/header.component';
import { PedidoService } from '../../services/pedido.service';
// import { Pedido } from '../../models/pedido.model';
import { HttpErrorResponse } from '@angular/common/http';
// import { Endereco } from '../../models/endereco.model';
// import { ItemPedido } from '../../models/itemPedido.model';
import { BetoneiraService } from '../../services/betoneira.service';
import { Betoneira } from '../../models/betoneira.model';

@Component({
    selector: 'app-compra',
    standalone: true,
    templateUrl: './compra.component.html',
    styleUrls: ['./compra.component.css'],
    imports: [NgIf,ReactiveFormsModule,CommonModule,MatCardModule,MatButtonModule,NgFor,MatCardActions,MatCardContent,MatCardTitle,MatCardFooter,HeaderComponent,FooterComponent]
})
export class ConfirmarCompraComponent implements OnInit,OnDestroy {
    private subscription = new Subscription();
    carrinhoItens: ItemCarrinho[] = [];
    userRole: string | null = null;
    usuarioLogado: Usuario | null = null;
    enderecoForm: FormGroup;
    betoneiras: Betoneira[] = [];

    constructor(
        private router: Router,
        private formBuilder: FormBuilder,
        private sidebarService: SidebarService,
        private authService: AuthService,
        private carrinhoService: CarrinhoService,
        private pedidoService: PedidoService,
        private betoneiraService: BetoneiraService,
        private localStorageService: LocalStorageService
    ) {
        this.enderecoForm = this.formBuilder.group({
            email: [{ value: '',disabled: true }],
            rua: [''],
            numero: [''],
            cep: [''],
            cidade: [''],
            estado: ['']
        });
    }

    ngOnInit(): void {
        this.carrinhoService.carrinhos.subscribe((items: ItemCarrinho[]) => {
            this.carrinhoItens = items;
        });
        this.subscription.add(this.authService.getUsuarioLogado().subscribe(usuario => {
            this.usuarioLogado = usuario;
            this.userRole = this.authService.getUserRole();
            if(this.usuarioLogado) {
                this.enderecoForm.patchValue({
                    numero: this.usuarioLogado.endereco.numero;
                    complemento: this.usuarioLogado.endereco.complemento;
                    bairro: this.usuarioLogado.endereco.bairro;
                    cep: this.usuarioLogado.endereco.cep;
                    municipio: this.usuarioLogado.endereco.municipio;

                    email: this.usuarioLogado.email,
                    rua: this.usuarioLogado.endereco?.rua || '',
                    numero: this.usuarioLogado.endereco?.numero || '',
                    cep: this.usuarioLogado.endereco?.cep || '',
                    cidade: this.usuarioLogado.endereco?.cidade || '',
                    estado: this.usuarioLogado.endereco?.estado || ''
                });
            }
        }));
        this.subscription.add(this.betoneiraService.findAll().subscribe(betoneiras => {
            this.betoneiras = betoneiras;
        }));
        this.betoneiraService.findAll().subscribe((data: Betoneira[]) => {
            this.betoneiras = data;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    calcularTotal(): number {
        return this.carrinhoItens.reduce((total,item) => total + item.quantidade * item.preco,0);
    }

    salvar(): void {
        this.pedidoService.insert(
            this.carrinhoService.obter(),
            this.enderecoForm.value
        ).subscribe(() => {
            this.router.navigateByUrl('/meuspedidos');
        },error => {
console.log(error);

            this.tratarErros(error);
        });
        /*
                this.pedidoService.insert({
                    usuario: this.usuarioLogado,
                    itens: this.carrinhoService.obter(), 
                    preco: this.carrinhoItens.reduce((total,item) => total + item.quantidade * item.preco,0),
                    endereco: this.enderecoForm.value,
                    tipoPagamento: this.enderecoForm.value.payment,
                    estadoPagamento: PagamentoEstado.PENDENTE
                }).subscribe(() => {
                    this.router.navigateByUrl('/meuspedidos');
                },error => {
                    this.tratarErros(error);
                });
        */
    }

    getErrorMessage(controlName: string,errors: ValidationErrors | null | undefined): string {
        if(!errors) return "";
        for(const errorName in errors) {
            if(errors.hasOwnProperty(errorName) && this.errorMessages[controlName][errorName]) {
                return this.errorMessages[controlName][errorName];
            }
        }
        return "Parâmetro inválido.";
    }

    tratarErros(errorResponse: HttpErrorResponse) {
        if(errorResponse.status === 400) {
            if(errorResponse.error?.errors) {
                errorResponse.error.errors.forEach((validationError: any) => {
                    const formControl = this.enderecoForm.get(validationError.fieldName);
                    if(formControl) {
                        formControl.setErrors({ apiError: validationError.message })
                    }
                });
            }
        } else if(errorResponse.status < 400) {
        } else if(errorResponse.status >= 500) {
        }
    }

    errorMessages: { [controlName: string]: { [errorName: string]: string } } = {
        username: {
            required: 'Nome de usuário é obrigatório.',
            minlength: 'Nome deve conter ao menos 4 caracteres.',
            maxlength: 'Nome deve conter no máximo 80 caracteres.',
            apiError: 'API_ERROR'
        },
        email: {
            required: 'Email é obrigatório.',
            minlength: 'Email deve conter ao menos 6 caracteres.',
            maxlength: 'Email deve conter no máximo 60 caracteres.',
            apiError: 'API_ERROR'
        },
        senha: {
            required: 'Senha é obrigatória.',
            minlength: 'Senha deve conter ao menos 6 caracteres.',
            maxlength: 'Senha deve conter no máximo 60 caracteres.',
            apiError: 'API_ERROR'
        },
        cpf: {
            required: 'CPF é obrigatório.',
            minlength: 'CPF deve conter ao menos 10 caracteres.',
            maxlength: 'CPF deve conter no máximo 12 caracteres.',
            apiError: 'API_ERROR'
        },
    }
}