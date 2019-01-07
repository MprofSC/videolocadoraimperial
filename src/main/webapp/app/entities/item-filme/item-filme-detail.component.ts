import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemFilme } from 'app/shared/model/item-filme.model';

@Component({
    selector: 'jhi-item-filme-detail',
    templateUrl: './item-filme-detail.component.html'
})
export class ItemFilmeDetailComponent implements OnInit {
    itemFilme: IItemFilme;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemFilme }) => {
            this.itemFilme = itemFilme;
        });
    }

    previousState() {
        window.history.back();
    }
}
