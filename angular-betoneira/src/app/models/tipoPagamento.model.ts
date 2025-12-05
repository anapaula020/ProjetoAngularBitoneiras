export enum Status {
    PIX = "PIX",
    CARTAO_CREDITO = "Cartão de crédito",
    BOLETO = "Boleto",
}

export const StatusMap: Record<number,Status> = {
    1: Status.PIX, 
    2: Status.CARTAO_CREDITO, 
    3: Status.BOLETO
};

export function getStatusById(id: number): Status {
    const status = StatusMap[id];
    if(!status) {
        throw new Error(`Id pagamento inválido: "${id}" não é 1 nem 2.`);
    }
    return status;
}