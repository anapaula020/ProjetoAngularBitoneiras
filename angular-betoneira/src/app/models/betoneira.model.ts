import { AutorBetoneira } from "./autorBetoneira.model";

export class Betoneira {
    id!: number;
    nome!: string;
    description!: string; // descricao
    preco!: number;
    idTipoBetoneira!: number;
    idAutor!: AutorBetoneira; // fabricante
    imageUrl!: string;

    constructor(
        id: number,
        nome: string,
        description: string,
        preco: number,
        idTipoBetoneira: number,
        idAutor: AutorBetoneira,
        imageUrl: string
    ) {
        this.id = id;
        this.nome = nome;
        this.description = description;
        this.preco = preco;
        this.idTipoBetoneira = idTipoBetoneira;
        this.idAutor = idAutor;
        this.imageUrl = imageUrl;
    }
}