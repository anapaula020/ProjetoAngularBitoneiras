export class Usuario {
    id!: number;
    username!: string;
    isAdmin!: boolean;
    isUser!: boolean;
    message!: string;

    constructor(id: number,username: string,isAdmin: boolean,isUser: boolean,message: string) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
        this.isUser = isUser;
        this.message = message;
    }
}