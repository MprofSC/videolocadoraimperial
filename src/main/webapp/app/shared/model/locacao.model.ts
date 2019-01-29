import { Moment } from 'moment';
import { ICliente } from 'app/shared/model//cliente.model';
import { IItemFilme } from 'app/shared/model//item-filme.model';

export interface ILocacao {
    id?: number;
    dataLocacao?: Moment;
    valor?: number;
    cliente?: ICliente;
    itemLocados?: IItemFilme[];
}

export class Locacao implements ILocacao {
    constructor(
        public id?: number,
        public dataLocacao?: Moment,
        public valor?: number,
        public cliente?: ICliente,
        public itemLocados?: IItemFilme[]
    ) {}
}
