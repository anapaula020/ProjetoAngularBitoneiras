export enum Status {
    PENDENTE = "Pendente",
    PAGO = "Pago",
    CANCELADO = "Cancelado",
    ENVIADO = "Enviado",
    ENTREGUE = "Entregue",
    PROCESSANDO = "Processando"
}

export const StatusMap: Record<number,Status> = {
    1: Status.PENDENTE, 
    2: Status.PAGO, 
    3: Status.CANCELADO, 
    4: Status.ENVIADO, 
    5: Status.ENTREGUE, 
    6: Status.PROCESSANDO
};

export function getStatusById(id: number): Status {
    const status = StatusMap[id];
    if(!status) {
        throw new Error(`Status inválido: "${id}" não é 1 nem 2.`);
    }
    return status;
}