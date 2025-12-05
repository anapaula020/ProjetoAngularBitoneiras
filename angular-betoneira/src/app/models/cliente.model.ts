export class Cliente {
    id!: number;
    nome!: string;
    email!: string;
    cpf!: string;
    senha!: string;

    constructor(id: number, nome: string,email: string,cpf: string,senha: string) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }
}