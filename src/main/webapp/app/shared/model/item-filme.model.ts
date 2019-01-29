import { Moment } from 'moment';
import { IFilme } from 'app/shared/model//filme.model';
import { IReserva } from 'app/shared/model//reserva.model';
import { ILocacao } from 'app/shared/model//locacao.model';

export const enum TipoMidia {
    DVD = 'DVD',
    VHS = 'VHS',
    HDDVD = 'HDDVD',
    BLURAY = 'BLURAY'
}

export interface IItemFilme {
    id?: number;
    numeroSerie?: string;
    tipoMidia?: TipoMidia;
    dataAquisicao?: Moment;
    filme?: IFilme;
    reservas?: IReserva[];
    locacaos?: ILocacao[];
}

export class ItemFilme implements IItemFilme {
    constructor(
        public id?: number,
        public numeroSerie?: string,
        public tipoMidia?: TipoMidia,
        public dataAquisicao?: Moment,
        public filme?: IFilme,
        public reservas?: IReserva[],
        public locacaos?: ILocacao[]
    ) {}
}
