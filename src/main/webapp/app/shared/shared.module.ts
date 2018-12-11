import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { VlimperialSharedLibsModule, VlimperialSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [VlimperialSharedLibsModule, VlimperialSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [VlimperialSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VlimperialSharedModule {
    static forRoot() {
        return {
            ngModule: VlimperialSharedModule
        };
    }
}
