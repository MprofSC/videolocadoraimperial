import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemFilme } from 'app/shared/model/item-filme.model';

type EntityResponseType = HttpResponse<IItemFilme>;
type EntityArrayResponseType = HttpResponse<IItemFilme[]>;

@Injectable({ providedIn: 'root' })
export class ItemFilmeService {
    public resourceUrl = SERVER_API_URL + 'api/item-filmes';

    constructor(private http: HttpClient) {}

    create(itemFilme: IItemFilme): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(itemFilme);
        return this.http
            .post<IItemFilme>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(itemFilme: IItemFilme): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(itemFilme);
        return this.http
            .put<IItemFilme>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IItemFilme>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IItemFilme[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(itemFilme: IItemFilme): IItemFilme {
        const copy: IItemFilme = Object.assign({}, itemFilme, {
            dataAquisicao: itemFilme.dataAquisicao != null && itemFilme.dataAquisicao.isValid() ? itemFilme.dataAquisicao.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataAquisicao = res.body.dataAquisicao != null ? moment(res.body.dataAquisicao) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((itemFilme: IItemFilme) => {
                itemFilme.dataAquisicao = itemFilme.dataAquisicao != null ? moment(itemFilme.dataAquisicao) : null;
            });
        }
        return res;
    }
}
