export class Administrador {
    id!: number;
    username!: string;
    email!: string;
    senha!: string;
    cpf!: string;

    constructor(id: number,username: string,email: string,senha: string,cpf: string) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }
}