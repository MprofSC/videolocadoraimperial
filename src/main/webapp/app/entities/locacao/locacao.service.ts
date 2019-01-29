import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocacao } from 'app/shared/model/locacao.model';

type EntityResponseType = HttpResponse<ILocacao>;
type EntityArrayResponseType = HttpResponse<ILocacao[]>;

@Injectable({ providedIn: 'root' })
export class LocacaoService {
    public resourceUrl = SERVER_API_URL + 'api/locacaos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/locacaos';

    constructor(private http: HttpClient) {}

    create(locacao: ILocacao): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(locacao);
        return this.http
            .post<ILocacao>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(locacao: ILocacao): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(locacao);
        return this.http
            .put<ILocacao>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILocacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILocacao[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILocacao[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(locacao: ILocacao): ILocacao {
        const copy: ILocacao = Object.assign({}, locacao, {
            dataLocacao: locacao.dataLocacao != null && locacao.dataLocacao.isValid() ? locacao.dataLocacao.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataLocacao = res.body.dataLocacao != null ? moment(res.body.dataLocacao) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((locacao: ILocacao) => {
                locacao.dataLocacao = locacao.dataLocacao != null ? moment(locacao.dataLocacao) : null;
            });
        }
        return res;
    }
}
