import { Telefone } from "./telefone.model";

export class Aluno {
    id?: number|null;
    nome?: string|null;
    sobrenome?: string|null;
    dataNascimento?: string|null;
    cpf?: string|null;
    email?: string|null;
    telefones?: Telefone[]|null;
}
