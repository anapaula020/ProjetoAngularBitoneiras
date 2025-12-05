import { PagamentoEstado } from "./PagamentoEstado.model";
import { PagamentoTipo } from "./PagamentoTipo.model";
import { Endereco } from "./endereco.model";
import { ItemPedido } from "./itemPedido.model";
import { Usuario } from "./usuario.model";

export class Pedido {
    id!: number;
    usuario!: Usuario;
    itens!: ItemPedido[];
    preco!: number;
    endereco!: Endereco;
    tipoPagamento!: PagamentoTipo;
    estadoPagamento!: PagamentoEstado;

    constructor(id: number, usuario: Usuario, itens: ItemPedido[], preco: number, endereco: Endereco, tipoPagamento: PagamentoTipo, estadoPagamento: PagamentoEstado) {
        this.id = id;
        this.usuario = usuario;
        this.itens = itens;
        this.preco = preco;
        this.endereco = endereco;
        this.tipoPagamento = tipoPagamento;
        this.estadoPagamento = estadoPagamento;
    }
}