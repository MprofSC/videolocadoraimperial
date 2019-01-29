import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VlimperialSharedModule } from 'app/shared';
import {
    LocacaoComponent,
    LocacaoDetailComponent,
    LocacaoUpdateComponent,
    LocacaoDeletePopupComponent,
    LocacaoDeleteDialogComponent,
    locacaoRoute,
    locacaoPopupRoute
} from './';

const ENTITY_STATES = [...locacaoRoute, ...locacaoPopupRoute];

@NgModule({
    imports: [VlimperialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LocacaoComponent,
        LocacaoDetailComponent,
        LocacaoUpdateComponent,
        LocacaoDeleteDialogComponent,
        LocacaoDeletePopupComponent
    ],
    entryComponents: [LocacaoComponent, LocacaoUpdateComponent, LocacaoDeleteDialogComponent, LocacaoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VlimperialLocacaoModule {}
