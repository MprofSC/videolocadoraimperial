import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Filme } from 'app/shared/model/filme.model';
import { FilmeService } from './filme.service';
import { FilmeComponent } from './filme.component';
import { FilmeDetailComponent } from './filme-detail.component';
import { FilmeUpdateComponent } from './filme-update.component';
import { FilmeDeletePopupComponent } from './filme-delete-dialog.component';
import { IFilme } from 'app/shared/model/filme.model';

@Injectable({ providedIn: 'root' })
export class FilmeResolve implements Resolve<IFilme> {
    constructor(private service: FilmeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Filme> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Filme>) => response.ok),
                map((filme: HttpResponse<Filme>) => filme.body)
            );
        }
        return of(new Filme());
    }
}

export const filmeRoute: Routes = [
    {
        path: 'filme',
        component: FilmeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Filmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filme/:id/view',
        component: FilmeDetailComponent,
        resolve: {
            filme: FilmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Filmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filme/new',
        component: FilmeUpdateComponent,
        resolve: {
            filme: FilmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Filmes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filme/:id/edit',
        component: FilmeUpdateComponent,
        resolve: {
            filme: FilmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Filmes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filmePopupRoute: Routes = [
    {
        path: 'filme/:id/delete',
        component: FilmeDeletePopupComponent,
        resolve: {
            filme: FilmeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Filmes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
