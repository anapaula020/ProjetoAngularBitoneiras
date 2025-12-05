import { EscritorNovel } from "./escritorNovel.model";
import { GeneroNovel } from "./generoNovel.model";

export class Novel {
    id!: number;
    nome!: string;
    imageUrl!: string;
    paginas!: number;
    preco!: number;
    sinopse!: string;
    lancamento!: number;
    estoque!: number;
    genero!: GeneroNovel;
    capitulos!: number;
    idAutor!: EscritorNovel;

    constructor(id: number,nome: string,imageUrl: string,sinopse: string,idAutor: EscritorNovel,lancamento: number,preco: number,estoque: number,paginas: number,genero: GeneroNovel,capitulos: number) {
        this.id = id;
        this.nome = nome;
        this.imageUrl = imageUrl;
        this.sinopse = sinopse;
        this.idAutor = idAutor;
        this.lancamento = lancamento;
        this.preco = preco;
        this.estoque = estoque;
        this.paginas = paginas;
        this.genero = genero;
        this.capitulos = capitulos;
    }
}