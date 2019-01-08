import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VlimperialClienteModule } from './cliente/cliente.module';
import { VlimperialFilmeModule } from './filme/filme.module';
import { VlimperialItemFilmeModule } from './item-filme/item-filme.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        VlimperialClienteModule,
        VlimperialFilmeModule,
        VlimperialItemFilmeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VlimperialEntityModule {}
