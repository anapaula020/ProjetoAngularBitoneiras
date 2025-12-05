export class Pagamento {
    valor!: number;
    statusPagamento!: String;
    dataPagamento!: Date;
    idTipoPagamento!: number;

    constructor(valor: number,statusPagamento: string,dataPagamento: Date,idTipoPagamento: number) {
        this.valor = valor;
        this.statusPagamento = statusPagamento;
        this.dataPagamento = dataPagamento;
        this.idTipoPagamento = idTipoPagamento;
    }
}