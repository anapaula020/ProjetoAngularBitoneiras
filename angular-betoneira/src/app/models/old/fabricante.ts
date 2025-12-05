export class Fabricante {
    id!: number;
    nome!: string;
    cnpj!: string;

    constructor(id: number,nome: string,anoNascimento: number,cnpj: string) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
    }
}