import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemFilme } from 'app/shared/model/item-filme.model';
import { ItemFilmeService } from './item-filme.service';

@Component({
    selector: 'jhi-item-filme-delete-dialog',
    templateUrl: './item-filme-delete-dialog.component.html'
})
export class ItemFilmeDeleteDialogComponent {
    itemFilme: IItemFilme;

    constructor(private itemFilmeService: ItemFilmeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemFilmeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemFilmeListModification',
                content: 'Deleted an itemFilme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-filme-delete-popup',
    template: ''
})
export class ItemFilmeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemFilme }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemFilmeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.itemFilme = itemFilme;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
