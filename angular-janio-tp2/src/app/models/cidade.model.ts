import { Estado } from "./estado.model";

export class Cidade {
    id?: number|null|undefined;
    nome!: string;
    estado!: Estado;
}
