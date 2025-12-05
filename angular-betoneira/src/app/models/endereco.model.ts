export class Endereco {
    id!: number;
    numero!: string;
    complemento!: string;
    bairro!: string;
    cep!: string;
    municipio!: string;

    constructor(id: number,numero: string,complemento: string,bairro: string,cep: string,municipio: string) {
        this.id = id;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.municipio = municipio;
    }
}