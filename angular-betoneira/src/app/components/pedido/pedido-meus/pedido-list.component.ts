import { Component, OnInit } from '@angular/core';
import { Pedido } from '../../../models/pedido.model';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PedidoService } from '../../../services/pedido.service';
import { DecimalPipe, NgFor, NgIf } from '@angular/common';
import { PagamentoEstado } from '../../../models/PagamentoEstado.model';

@Component({
    imports: [NgFor, DecimalPipe, NgIf],
    selector: 'app-pedido-list',
    templateUrl: './pedido-list.component.html',
    styleUrls: ['./pedido-list.component.css']
})
export class PedidosMeusComponent implements OnInit {
    pedidos: Pedido[] = [];
    PagamentoEstado = PagamentoEstado;
    constructor(
        private pedidoService: PedidoService,
        private router: Router,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit(): void {
        this.pedidoService.findMyPedidos().subscribe((data: Pedido[]) => {
            this.pedidos = data.map(pedido => ({
                ...pedido,
                estadoPagamento: PagamentoEstado[pedido.estadoPagamento as keyof typeof PagamentoEstado]
            }));
        });
    }
    
    pagarPix(id: number): void {
        this.router.navigate([`/meuspedidos/pagarpix/${id}`]);
    }

    pagarCredito(id: number): void {
        this.router.navigate([`/meuspedidos/pagarcredito/${id}`]);
    }

    pagarDebito(id: number): void {
        this.router.navigate([`/meuspedidos/pagardebito/${id}`]);
    }
}