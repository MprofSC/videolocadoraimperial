import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFilme } from 'app/shared/model/filme.model';
import { Principal } from 'app/core';
import { FilmeService } from './filme.service';

@Component({
    selector: 'jhi-filme',
    templateUrl: './filme.component.html'
})
export class FilmeComponent implements OnInit, OnDestroy {
    filmes: IFilme[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private filmeService: FilmeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.filmeService.query().subscribe(
            (res: HttpResponse<IFilme[]>) => {
                this.filmes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFilmes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFilme) {
        return item.id;
    }

    registerChangeInFilmes() {
        this.eventSubscriber = this.eventManager.subscribe('filmeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
