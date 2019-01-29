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
import { ILocacao } from 'app/shared/model/locacao.model';
import { LocacaoService } from 'app/entities/locacao';
import { IReserva } from 'app/shared/model/reserva.model';
import { ReservaService } from 'app/entities/reserva';

@Component({
    selector: 'jhi-item-filme-update',
    templateUrl: './item-filme-update.component.html'
})
export class ItemFilmeUpdateComponent implements OnInit {
    itemFilme: IItemFilme;
    isSaving: boolean;

    filmes: IFilme[];

    locacaos: ILocacao[];

    reservas: IReserva[];
    dataAquisicao: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private itemFilmeService: ItemFilmeService,
        private filmeService: FilmeService,
        private locacaoService: LocacaoService,
        private reservaService: ReservaService,
        private activatedRoute: ActivatedRoute
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
        this.locacaoService.query().subscribe(
            (res: HttpResponse<ILocacao[]>) => {
                this.locacaos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.reservaService.query().subscribe(
            (res: HttpResponse<IReserva[]>) => {
                this.reservas = res.body;
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

    private subscribeToSaveResponse(result: Observable<HttpResponse<IItemFilme>>) {
        result.subscribe((res: HttpResponse<IItemFilme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFilmeById(index: number, item: IFilme) {
        return item.id;
    }

    trackLocacaoById(index: number, item: ILocacao) {
        return item.id;
    }

    trackReservaById(index: number, item: IReserva) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
