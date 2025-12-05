export class Pix {
    id!: string;
    status!: string;
    qrCode!: string;
    qrCodeBase64!: string;
    ticketUrl!: string;
    amount!: number;
    email!: string;
    firstName!: string;
    lastName!: string;
    identificationType!: string;
    identificationNumber!: string;

    constructor(
        id: string,
        status: string,
        qrCode: string,
        qrCodeBase64: string,
        ticketUrl: string,
        amount: number,
        email: string,
        firstName: string,
        lastName: string,
        identificationType: string,
        identificationNumber: string
    ) {
        this.id = id;
        this.status = status;
        this.qrCode = qrCode;
        this.qrCodeBase64 = qrCodeBase64;
        this.ticketUrl = ticketUrl;
        this.amount = amount;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationType = identificationType;
        this.identificationNumber = identificationNumber;
    }
}