import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VideoLocadoraImperialClienteModule } from './cliente/cliente.module';
import { VideoLocadoraImperialFilmeModule } from './filme/filme.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        VideoLocadoraImperialClienteModule,
        VideoLocadoraImperialFilmeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VideoLocadoraImperialEntityModule {}
