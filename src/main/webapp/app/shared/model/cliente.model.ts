import { Moment } from 'moment';
import { ICliente } from 'app/shared/model//cliente.model';
import { IReserva } from 'app/shared/model//reserva.model';
import { ILocacao } from 'app/shared/model//locacao.model';

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
    ativo?: boolean;
    cliente?: ICliente;
    clientes?: ICliente[];
    reservas?: IReserva[];
    locacaos?: ILocacao[];
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
        public ativo?: boolean,
        public cliente?: ICliente,
        public clientes?: ICliente[],
        public reservas?: IReserva[],
        public locacaos?: ILocacao[]
    ) {
        this.ativo = this.ativo || false;
    }
}
