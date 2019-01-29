import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IReserva } from 'app/shared/model/reserva.model';
import { ReservaService } from './reserva.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IItemFilme } from 'app/shared/model/item-filme.model';
import { ItemFilmeService } from 'app/entities/item-filme';

@Component({
    selector: 'jhi-reserva-update',
    templateUrl: './reserva-update.component.html'
})
export class ReservaUpdateComponent implements OnInit {
    reserva: IReserva;
    isSaving: boolean;

    clientes: ICliente[];

    itemfilmes: IItemFilme[];
    dataSolicitacao: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private reservaService: ReservaService,
        private clienteService: ClienteService,
        private itemFilmeService: ItemFilmeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reserva }) => {
            this.reserva = reserva;
            this.dataSolicitacao = this.reserva.dataSolicitacao != null ? this.reserva.dataSolicitacao.format(DATE_TIME_FORMAT) : null;
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
        this.reserva.dataSolicitacao = this.dataSolicitacao != null ? moment(this.dataSolicitacao, DATE_TIME_FORMAT) : null;
        if (this.reserva.id !== undefined) {
            this.subscribeToSaveResponse(this.reservaService.update(this.reserva));
        } else {
            this.subscribeToSaveResponse(this.reservaService.create(this.reserva));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReserva>>) {
        result.subscribe((res: HttpResponse<IReserva>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
