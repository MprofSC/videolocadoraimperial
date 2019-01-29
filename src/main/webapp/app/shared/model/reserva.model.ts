import { Moment } from 'moment';
import { ICliente } from 'app/shared/model//cliente.model';
import { IItemFilme } from 'app/shared/model//item-filme.model';

export interface IReserva {
    id?: number;
    dataSolicitacao?: Moment;
    cliente?: ICliente;
    midiaDesejadas?: IItemFilme[];
}

export class Reserva implements IReserva {
    constructor(public id?: number, public dataSolicitacao?: Moment, public cliente?: ICliente, public midiaDesejadas?: IItemFilme[]) {}
}
