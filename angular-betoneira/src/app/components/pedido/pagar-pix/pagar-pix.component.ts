import { Component,OnInit } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,Validators } from '@angular/forms';
import { ActivatedRoute,Router } from '@angular/router';
import { PedidoService } from '../../../services/pedido.service';
import { AuthService } from '../../../services/auth.service';
import { Subscription } from 'rxjs';
import { NgIf } from '@angular/common';
import { Cliente } from '../../../models/cliente.model';

@Component({
    selector: 'app-pagar-pix',
    templateUrl: './pagar-pix.component.html',
    styleUrls: ['./pagar-pix.component.css'],
    imports: [ReactiveFormsModule, NgIf]
})
export class PagarPixComponent implements OnInit {
    pagarPixForm: FormGroup;
    pedidoId!: number;
    clienteLogado: Cliente | null = null;
    private subscription = new Subscription();

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private pedidoService: PedidoService,
        private authService: AuthService
    ) {
        this.pagarPixForm = this.formBuilder.group({
            cpf: ['',[Validators.required,Validators.pattern(/^\d{11}$/)]],
            valor: ['',[Validators.required,Validators.min(0.01)]]
        });
    }

    ngOnInit(): void {
        this.pedidoId = +this.route.snapshot.paramMap.get('id')!;
        this.subscription.add(this.authService.getClienteLogado().subscribe(data => {
            this.clienteLogado = data;
            if(this.clienteLogado && this.clienteLogado.cpf) {
                this.pagarPixForm.patchValue({ cpf: this.clienteLogado.cpf });
            }
        }));
    }

    onSubmit(): void {
        if(this.pagarPixForm.valid) {
            const { cpf,valor } = this.pagarPixForm.value;
            this.pedidoService.pagarPeloPix(this.pedidoId,cpf,valor).subscribe({
                next: () => {
                    alert('Pagamento pelo Pix realizado com sucesso!');
                    this.router.navigate(['/meuspedidos']);
                },
                error: (err) => {
                    alert('Erro ao realizar o pagamento pelo Pix.');
                    console.error('Erro ao realizar o pagamento pelo Pix',err);
                }
            });
        }
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}