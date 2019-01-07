import { NgModule } from '@angular/core';

import { VlimperialSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [VlimperialSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [VlimperialSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class VlimperialSharedCommonModule {}
