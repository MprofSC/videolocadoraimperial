/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VideoLocadoraImperialTestModule } from '../../../test.module';
import { FilmeDeleteDialogComponent } from 'app/entities/filme/filme-delete-dialog.component';
import { FilmeService } from 'app/entities/filme/filme.service';

describe('Component Tests', () => {
    describe('Filme Management Delete Component', () => {
        let comp: FilmeDeleteDialogComponent;
        let fixture: ComponentFixture<FilmeDeleteDialogComponent>;
        let service: FilmeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VideoLocadoraImperialTestModule],
                declarations: [FilmeDeleteDialogComponent]
            })
                .overrideTemplate(FilmeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FilmeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilmeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
