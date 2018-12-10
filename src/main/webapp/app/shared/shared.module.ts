import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { VideoLocadoraImperialSharedLibsModule, VideoLocadoraImperialSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [VideoLocadoraImperialSharedLibsModule, VideoLocadoraImperialSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [VideoLocadoraImperialSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VideoLocadoraImperialSharedModule {
    static forRoot() {
        return {
            ngModule: VideoLocadoraImperialSharedModule
        };
    }
}
