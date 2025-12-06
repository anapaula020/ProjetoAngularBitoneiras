export class Cliente {
    id!: number;
    username!: string;
    email!: string;
    cpf!: string;
    senha!: string;
    endereco!: string;

    constructor(id: number,username: string,email: string,cpf: string,senha: string,endereco: string) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.endereco = endereco;
    }
}