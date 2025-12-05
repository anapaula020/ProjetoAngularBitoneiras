export class Betoneira {
    id!: number;
    nome!: string;
    descricao!: string;
    preco!: number;
    idTipoBetoneira!: number;
    idFabricante!: number;

    constructor(
        id: number,
        nome: string,
        descricao: string,
        preco: number,
        idTipoBetoneira: number,
        idFabricante: number
    ) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.idTipoBetoneira = idTipoBetoneira;
        this.idFabricante = idFabricante;
    }
}