import { Moment } from 'moment';
import { ICliente } from 'app/shared/model//cliente.model';

export const enum GeneroPessoa {
    MASCULINO = 'MASCULINO',
    FEMININO = 'FEMININO',
    INDEFINIDO = 'INDEFINIDO'
}

export interface ICliente {
    id?: number;
    numeroIncricao?: number;
    nome?: string;
    cpf?: number;
    email?: string;
    endereco?: string;
    telefoneResidencial?: string;
    telefoneComercial?: string;
    telefoneCelular?: string;
    localTrabalho?: string;
    sexo?: GeneroPessoa;
    dataNascimento?: Moment;
    cliente?: ICliente;
    numeroIncricaos?: ICliente[];
}

export class Cliente implements ICliente {
    constructor(
        public id?: number,
        public numeroIncricao?: number,
        public nome?: string,
        public cpf?: number,
        public email?: string,
        public endereco?: string,
        public telefoneResidencial?: string,
        public telefoneComercial?: string,
        public telefoneCelular?: string,
        public localTrabalho?: string,
        public sexo?: GeneroPessoa,
        public dataNascimento?: Moment,
        public cliente?: ICliente,
        public numeroIncricaos?: ICliente[]
    ) {}
}
