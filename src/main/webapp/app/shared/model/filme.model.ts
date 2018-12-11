export const enum Genero {
    ACAO = 'ACAO',
    ANIMACAO = 'ANIMACAO',
    AVENTURA = 'AVENTURA',
    COMEDIA = 'COMEDIA',
    DOCUMENTARIO = 'DOCUMENTARIO',
    DRAMA = 'DRAMA',
    FICCAO = 'FICCAO',
    GUERRA = 'GUERRA',
    MUSICAL = 'MUSICAL',
    POLICIAL = 'POLICIAL',
    ROMANCE = 'ROMANCE',
    SUSPENSE = 'SUSPENSE',
    TERROR = 'TERROR'
}

export interface IFilme {
    id?: number;
    tituloOriginal?: string;
    tituloPortugues?: string;
    paises?: string;
    ano?: number;
    direcao?: string;
    elenco?: string;
    sinopse?: string;
    duracao?: string;
    genero?: Genero;
}

export class Filme implements IFilme {
    constructor(
        public id?: number,
        public tituloOriginal?: string,
        public tituloPortugues?: string,
        public paises?: string,
        public ano?: number,
        public direcao?: string,
        public elenco?: string,
        public sinopse?: string,
        public duracao?: string,
        public genero?: Genero
    ) {}
}
