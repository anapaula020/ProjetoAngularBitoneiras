import { Endereco } from "./endereco.model";
import { Sexo } from "./sexo.model";

export class Usuario {
    id!: number;
    username!: string;
    email!: string;
    senha!: string;
    cpf!: string;
    endereco!: Endereco;
    sexo!: Sexo;

    constructor(username: string,email: string,senha: string,cpf: string,endereco: Endereco,sexo: Sexo) {
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.endereco = endereco;
        this.sexo = sexo;
    }
}