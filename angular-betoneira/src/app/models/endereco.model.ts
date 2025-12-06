export class Endereco {
    id!: number;
    rua!: string;
    numero!: string;
    cep!: string;
    cidade!: string;
    estado!: string;

    constructor(id: number,rua: string,numero: string,cep: string,cidade: string,estado: string) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }
}