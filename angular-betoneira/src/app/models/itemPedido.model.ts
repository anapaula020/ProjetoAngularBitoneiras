import { Betoneira } from "./betoneira.model"

export  class ItemPedido{
    idBetoneira!: number;
    betoneira!: Betoneira;
    preco!: number;
    desconto!: number;
    quantidade!: number

    constructor(idBetoneira: number, betoneira: Betoneira, preco: number, desconto: number, quantidade: number){
        this.idBetoneira = idBetoneira;
        this.betoneira = betoneira;
        this.preco = preco;
        this.desconto = desconto;
        this.quantidade = quantidade;
    };
}