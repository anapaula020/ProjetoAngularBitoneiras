import { Regiao } from "./regiao.model";

export class Estado {
    id?: number|null;
    nome?: string|null;
    sigla?: string|null;
    regiao?: Regiao|null;
}
