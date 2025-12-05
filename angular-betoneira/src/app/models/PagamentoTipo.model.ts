export enum PagamentoTipo {
    CREDITO = "Credito", 
    DEBITO = "Debito", 
    PIX = "Pix"
}

export const PagamentoTipoMap: Record<number,PagamentoTipo> = {
    1: PagamentoTipo.CREDITO, 
    2: PagamentoTipo.DEBITO, 
    3: PagamentoTipo.PIX
};

export function getPagamentoTipoById(id: number): PagamentoTipo {
    const novel = PagamentoTipoMap[id];
    if(!novel) {
        throw new Error(`PagamentoTipo inválido: "${id}" não é 1 nem 2.`);
    }
    return novel;
}