import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFilme } from 'app/shared/model/filme.model';

@Component({
    selector: 'jhi-filme-detail',
    templateUrl: './filme-detail.component.html'
})
export class FilmeDetailComponent implements OnInit {
    filme: IFilme;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filme }) => {
            this.filme = filme;
        });
    }

    previousState() {
        window.history.back();
    }
}
