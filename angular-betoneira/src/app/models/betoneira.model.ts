import { Fabricante } from "./fabricante.model";

export class Betoneira {
    id!: number;
    nome!: string;
    descricao!: string;
    preco!: number;
    quantidadeEstoque!: number;
    modelo!: string;
    marca!: string;
    capacidade!: number;
    idTipoBetoneira!: number;
    idFabricante!: Fabricante;
    imageUrl!: string;

    constructor(id: number,nome: string,descricao: string,preco: number,quantidadeEstoque: number,modelo: string,marca: string,capacidade: number,idTipoBetoneira: number,idFabricante: Fabricante,imageUrl: string) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.modelo = modelo;
        this.marca = marca;
        this.capacidade = capacidade;
        this.idTipoBetoneira = idTipoBetoneira;
        this.idFabricante = idFabricante;
        this.imageUrl = imageUrl;
    }
}