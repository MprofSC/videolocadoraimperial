import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ILocacao } from 'app/shared/model/locacao.model';
import { LocacaoService } from './locacao.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IItemFilme } from 'app/shared/model/item-filme.model';
import { ItemFilmeService } from 'app/entities/item-filme';

@Component({
    selector: 'jhi-locacao-update',
    templateUrl: './locacao-update.component.html'
})
export class LocacaoUpdateComponent implements OnInit {
    locacao: ILocacao;
    isSaving: boolean;

    clientes: ICliente[];

    itemfilmes: IItemFilme[];
    dataLocacao: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private locacaoService: LocacaoService,
        private clienteService: ClienteService,
        private itemFilmeService: ItemFilmeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ locacao }) => {
            this.locacao = locacao;
            this.dataLocacao = this.locacao.dataLocacao != null ? this.locacao.dataLocacao.format(DATE_TIME_FORMAT) : null;
        });
        this.clienteService.query().subscribe(
            (res: HttpResponse<ICliente[]>) => {
                this.clientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.itemFilmeService.query().subscribe(
            (res: HttpResponse<IItemFilme[]>) => {
                this.itemfilmes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.locacao.dataLocacao = this.dataLocacao != null ? moment(this.dataLocacao, DATE_TIME_FORMAT) : null;
        if (this.locacao.id !== undefined) {
            this.subscribeToSaveResponse(this.locacaoService.update(this.locacao));
        } else {
            this.subscribeToSaveResponse(this.locacaoService.create(this.locacao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILocacao>>) {
        result.subscribe((res: HttpResponse<ILocacao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }

    trackItemFilmeById(index: number, item: IItemFilme) {
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
