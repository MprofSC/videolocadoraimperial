import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IItemFilme } from 'app/shared/model/item-filme.model';
import { ItemFilmeService } from './item-filme.service';
import { IFilme } from 'app/shared/model/filme.model';
import { FilmeService } from 'app/entities/filme';

@Component({
    selector: 'jhi-item-filme-update',
    templateUrl: './item-filme-update.component.html'
})
export class ItemFilmeUpdateComponent implements OnInit {
    itemFilme: IItemFilme;
    isSaving: boolean;

    filmes: IFilme[];
    dataAquisicao: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected itemFilmeService: ItemFilmeService,
        protected filmeService: FilmeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemFilme }) => {
            this.itemFilme = itemFilme;
            this.dataAquisicao = this.itemFilme.dataAquisicao != null ? this.itemFilme.dataAquisicao.format(DATE_TIME_FORMAT) : null;
        });
        this.filmeService.query().subscribe(
            (res: HttpResponse<IFilme[]>) => {
                this.filmes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.itemFilme.dataAquisicao = this.dataAquisicao != null ? moment(this.dataAquisicao, DATE_TIME_FORMAT) : null;
        if (this.itemFilme.id !== undefined) {
            this.subscribeToSaveResponse(this.itemFilmeService.update(this.itemFilme));
        } else {
            this.subscribeToSaveResponse(this.itemFilmeService.create(this.itemFilme));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemFilme>>) {
        result.subscribe((res: HttpResponse<IItemFilme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFilmeById(index: number, item: IFilme) {
        return item.id;
    }
}
