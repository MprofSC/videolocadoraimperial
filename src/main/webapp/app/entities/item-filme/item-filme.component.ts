import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IItemFilme } from 'app/shared/model/item-filme.model';
import { Principal } from 'app/core';
import { ItemFilmeService } from './item-filme.service';

@Component({
    selector: 'jhi-item-filme',
    templateUrl: './item-filme.component.html'
})
export class ItemFilmeComponent implements OnInit, OnDestroy {
    itemFilmes: IItemFilme[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private itemFilmeService: ItemFilmeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.itemFilmeService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IItemFilme[]>) => (this.itemFilmes = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.itemFilmeService.query().subscribe(
            (res: HttpResponse<IItemFilme[]>) => {
                this.itemFilmes = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInItemFilmes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IItemFilme) {
        return item.id;
    }

    registerChangeInItemFilmes() {
        this.eventSubscriber = this.eventManager.subscribe('itemFilmeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
