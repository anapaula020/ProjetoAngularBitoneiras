import { Component,OnInit } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,Validators } from '@angular/forms';
import { ActivatedRoute,Router } from '@angular/router';
import { PedidoService } from '../../../services/pedido.service';
import { AuthService } from '../../../services/auth.service';
import { Usuario } from '../../../models/usuario.model';
import { Subscription } from 'rxjs';
import { NgIf } from '@angular/common';
import { Cliente } from '../../../models/cliente.model';

@Component({
    selector: 'app-pagar-cartao',
    templateUrl: './pagar-cartao.component.html',
    styleUrls: ['./pagar-cartao.component.css'],
    imports: [ReactiveFormsModule,NgIf],
    standalone: true
})
export class PagarCartaoComponent implements OnInit {
    pagarCartaoForm: FormGroup;
    pedidoId!: number;
    usuarioLogado: Cliente | null = null;
    private subscription = new Subscription();
    titulo: string = '';

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private pedidoService: PedidoService,
        private authService: AuthService
    ) {
        this.pagarCartaoForm = this.formBuilder.group({
            tipoPagamento: ['',Validators.required],
            numero: ['',[Validators.required,Validators.pattern(/^\d{16}$/)]],
            nome: ['',Validators.required],
            cvv: ['',[Validators.required,Validators.pattern(/^\d{3}$/)]],
            limite: ['',[Validators.required,Validators.min(0.01)]],
            parcelas: ['']
        });
    }

    ngOnInit(): void {
        this.pedidoId = +this.route.snapshot.paramMap.get('id')!;
        const tipoPagamento = this.route.snapshot.url[1].path === 'pagarcredito' ? 'credito' : 'debito';
        this.pagarCartaoForm.patchValue({ tipoPagamento });
        this.titulo = tipoPagamento === 'credito' ? 'Pagar com Cartão de Crédito' : 'Pagar com Cartão de Débito';
        this.subscription.add(this.authService.getUsuarioLogado().subscribe(usuario => {
            this.usuarioLogado = usuario;
        }));
    }

    onSubmit(): void {
        if(this.pagarCartaoForm.valid) {
            const { tipoPagamento,numero,nome,cvv,limite,parcelas } = this.pagarCartaoForm.value;
            if(tipoPagamento === 'credito') {
                this.pedidoService.pagarPeloCredito(this.pedidoId,numero,nome,cvv,limite,parcelas).subscribe({
                    next: () => {
                        alert('Pagamento pelo Crédito realizado com sucesso!');
                        this.router.navigate(['/meuspedidos']);
                    },
                    error: (err) => {
                        alert('Erro ao realizar o pagamento pelo Crédito.');
                        console.error('Erro ao realizar o pagamento pelo Crédito',err);
                    }
                });
            } else if(tipoPagamento === 'debito') {
                this.pedidoService.pagarPeloDebito(this.pedidoId,numero,nome,cvv,limite).subscribe({
                    next: () => {
                        alert('Pagamento pelo Débito realizado com sucesso!');
                        this.router.navigate(['/meuspedidos']);
                    },
                    error: (err) => {
                        alert('Erro ao realizar o pagamento pelo Débito.');
                        console.error('Erro ao realizar o pagamento pelo Débito',err);
                    }
                });
            }
        }
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}