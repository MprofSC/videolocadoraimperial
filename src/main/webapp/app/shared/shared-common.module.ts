import { NgModule } from '@angular/core';

import { VideoLocadoraImperialSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [VideoLocadoraImperialSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [VideoLocadoraImperialSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class VideoLocadoraImperialSharedCommonModule {}
