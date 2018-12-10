import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VideoLocadoraImperialSharedModule } from 'app/shared';
import {
    FilmeComponent,
    FilmeDetailComponent,
    FilmeUpdateComponent,
    FilmeDeletePopupComponent,
    FilmeDeleteDialogComponent,
    filmeRoute,
    filmePopupRoute
} from './';

const ENTITY_STATES = [...filmeRoute, ...filmePopupRoute];

@NgModule({
    imports: [VideoLocadoraImperialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FilmeComponent, FilmeDetailComponent, FilmeUpdateComponent, FilmeDeleteDialogComponent, FilmeDeletePopupComponent],
    entryComponents: [FilmeComponent, FilmeUpdateComponent, FilmeDeleteDialogComponent, FilmeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VideoLocadoraImperialFilmeModule {}
