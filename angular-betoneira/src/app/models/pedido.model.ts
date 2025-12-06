import { Cliente } from "./cliente.model";
import { PagamentoEstado } from "./PagamentoEstado.model";
import { PagamentoTipo } from "./PagamentoTipo.model";
import { Endereco } from "./endereco.model";
import { ItemPedido } from "./itemPedido.model";

export class Pedido {
    id!: number;
    cliente!: Cliente;
    itens!: ItemPedido[];
    preco!: number;
    endereco!: Endereco;
    tipoPagamento!: PagamentoTipo;
    estadoPagamento!: PagamentoEstado;

    constructor(id: number, cliente: Cliente, itens: ItemPedido[], preco: number, endereco: Endereco, tipoPagamento: PagamentoTipo, estadoPagamento: PagamentoEstado) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.preco = preco;
        this.endereco = endereco;
        this.tipoPagamento = tipoPagamento;
        this.estadoPagamento = estadoPagamento;
    }
}