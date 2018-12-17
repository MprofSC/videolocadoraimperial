import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemFilme } from 'app/shared/model/item-filme.model';
import { ItemFilmeService } from './item-filme.service';
import { ItemFilmeComponent } from './item-filme.component';
import { ItemFilmeDetailComponent } from './item-filme-detail.component';
import { ItemFilmeUpdateComponent } from './item-filme-update.component';
import { ItemFilmeDeletePopupComponent } from './item-filme-delete-dialog.component';
import { IItemFilme } from 'app/shared/model/item-filme.model';

@Injectable({ providedIn: 'root' })
export class ItemFilmeResolve implements Resolve<IItemFilme> {
    constructor(private service: ItemFilmeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ItemFilme> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemFilme>) => response.ok),
                map((itemFilme: HttpResponse<ItemFilme>) => itemFilme.body)
            );
        }
        return of(new ItemFilme());
    }
}

export const itemFilmeRoute: Routes = [
    {
        path: 'item-filme',
        component: ItemFilmeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemFilmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-filme/:id/view',
        component: ItemFilmeDetailComponent,
        resolve: {
            itemFilme: ItemFilmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemFilmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-filme/new',
        component: ItemFilmeUpdateComponent,
        resolve: {
            itemFilme: ItemFilmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemFilmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-filme/:id/edit',
        component: ItemFilmeUpdateComponent,
        resolve: {
            itemFilme: ItemFilmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemFilmes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemFilmePopupRoute: Routes = [
    {
        path: 'item-filme/:id/delete',
        component: ItemFilmeDeletePopupComponent,
        resolve: {
            itemFilme: ItemFilmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemFilmes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
