import { Manga } from "./manga.model"

export  class ItemPedido{
    idManga!: number;
    manga!: Manga;
    preco!: number;
    desconto!: number;
    quantidade!: number

    constructor(idManga: number, manga: Manga, preco: number, desconto: number, quantidade: number){
        this.idManga = idManga;
        this.manga = manga;
        this.preco = preco;
        this.desconto = desconto;
        this.quantidade = quantidade;
    };
}