import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFilme } from 'app/shared/model/filme.model';

type EntityResponseType = HttpResponse<IFilme>;
type EntityArrayResponseType = HttpResponse<IFilme[]>;

@Injectable({ providedIn: 'root' })
export class FilmeService {
    public resourceUrl = SERVER_API_URL + 'api/filmes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/filmes';

    constructor(protected http: HttpClient) {}

    create(filme: IFilme): Observable<EntityResponseType> {
        return this.http.post<IFilme>(this.resourceUrl, filme, { observe: 'response' });
    }

    update(filme: IFilme): Observable<EntityResponseType> {
        return this.http.put<IFilme>(this.resourceUrl, filme, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFilme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFilme[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFilme[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
