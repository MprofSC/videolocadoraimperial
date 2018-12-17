/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VlimperialTestModule } from '../../../test.module';
import { ItemFilmeDeleteDialogComponent } from 'app/entities/item-filme/item-filme-delete-dialog.component';
import { ItemFilmeService } from 'app/entities/item-filme/item-filme.service';

describe('Component Tests', () => {
    describe('ItemFilme Management Delete Component', () => {
        let comp: ItemFilmeDeleteDialogComponent;
        let fixture: ComponentFixture<ItemFilmeDeleteDialogComponent>;
        let service: ItemFilmeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VlimperialTestModule],
                declarations: [ItemFilmeDeleteDialogComponent]
            })
                .overrideTemplate(ItemFilmeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemFilmeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemFilmeService);
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
