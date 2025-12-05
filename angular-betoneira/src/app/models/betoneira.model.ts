import { Fabricante } from "./fabricante.model";
import { TipoBetoneira } from "./tipoBetoneira.model";

export class Betoneira {
    id!: number;
    nome!: string;
    descricao!: string;
    preco!: number;
    quantidadeEstoque!: number;
    modelo!: string;
    marca!: string;
    capacidade!: number;
    tipoBetoneira!: TipoBetoneira;
    idFabricante!: Fabricante;
    imageUrl!: string;

    constructor(id: number,nome: string,descricao: string,preco: number,quantidadeEstoque: number,modelo: string,marca: string,capacidade: number,tipoBetoneira: TipoBetoneira,idFabricante: Fabricante,imageUrl: string) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.modelo = modelo;
        this.marca = marca;
        this.capacidade = capacidade;
        this.tipoBetoneira = tipoBetoneira;
        this.idFabricante = idFabricante;
        this.imageUrl = imageUrl;
    }
}