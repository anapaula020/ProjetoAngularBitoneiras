export enum PagamentoEstado {
    PENDENTE = "PENDENTE", 
    APROVADO = "APROVADO", 
    RECUSADO = "RECUSADO", 
    REEMBOLSADO = "REEMBOLSADO", 
    CANCELADO = "CANCELADO", 
    PARCELAS = "PARCELAS"
}

export const PagamentoEstadoMap: Record<number,PagamentoEstado> = {
    1: PagamentoEstado.PENDENTE, 
    2: PagamentoEstado.APROVADO, 
    3: PagamentoEstado.RECUSADO, 
    4: PagamentoEstado.REEMBOLSADO, 
    5: PagamentoEstado.CANCELADO, 
    6: PagamentoEstado.PARCELAS
};

export function getPagamentoEstadoById(id: number): PagamentoEstado {
    const novel = PagamentoEstadoMap[id];
    if(!novel) {
        throw new Error(`PagamentoEstado inválido: "${id}" não é 1 nem 2.`);
    }
    return novel;
}