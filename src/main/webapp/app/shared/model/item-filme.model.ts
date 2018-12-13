import { Moment } from 'moment';
import { IFilme } from 'app/shared/model//filme.model';

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
}

export class ItemFilme implements IItemFilme {
    constructor(
        public id?: number,
        public numeroSerie?: string,
        public tipoMidia?: TipoMidia,
        public dataAquisicao?: Moment,
        public filme?: IFilme
    ) {}
}
