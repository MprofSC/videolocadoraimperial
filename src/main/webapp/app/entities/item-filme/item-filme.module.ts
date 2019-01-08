import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VlimperialSharedModule } from 'app/shared';
import {
    ItemFilmeComponent,
    ItemFilmeDetailComponent,
    ItemFilmeUpdateComponent,
    ItemFilmeDeletePopupComponent,
    ItemFilmeDeleteDialogComponent,
    itemFilmeRoute,
    itemFilmePopupRoute
} from './';

const ENTITY_STATES = [...itemFilmeRoute, ...itemFilmePopupRoute];

@NgModule({
    imports: [VlimperialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemFilmeComponent,
        ItemFilmeDetailComponent,
        ItemFilmeUpdateComponent,
        ItemFilmeDeleteDialogComponent,
        ItemFilmeDeletePopupComponent
    ],
    entryComponents: [ItemFilmeComponent, ItemFilmeUpdateComponent, ItemFilmeDeleteDialogComponent, ItemFilmeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VlimperialItemFilmeModule {}
