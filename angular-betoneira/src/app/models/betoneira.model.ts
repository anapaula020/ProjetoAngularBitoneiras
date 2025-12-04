import { GeneroBetoneira } from "./generoBetoneira.model";
import { AutorBetoneira } from "./autorBetoneira.model";

export class Betoneira {
    id!: number;
    nome!: string;
    imageUrl!: string;
    paginas!: number;
    preco!: number;
    sinopse!: string;
    lancamento!: number;
    estoque!: number;
    color!: string;
    idAutor!: AutorBetoneira;
    genero!: GeneroBetoneira;

    constructor(id: number,nome: string,imageUrl: string,paginas: number,preco: number,sinopse: string,lancamento: number,estoque: number,color: string,idAutor: AutorBetoneira,genero: GeneroBetoneira) {
        this.id = id;
        this.nome = nome;
        this.imageUrl = imageUrl;
        this.paginas = paginas;
        this.preco = preco;
        this.sinopse = sinopse;
        this.lancamento = lancamento;
        this.estoque = estoque;
        this.color = color;
        this.idAutor = idAutor;
        this.genero = genero;
    }
}