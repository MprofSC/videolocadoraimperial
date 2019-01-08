/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VlimperialTestModule } from '../../../test.module';
import { ItemFilmeUpdateComponent } from 'app/entities/item-filme/item-filme-update.component';
import { ItemFilmeService } from 'app/entities/item-filme/item-filme.service';
import { ItemFilme } from 'app/shared/model/item-filme.model';

describe('Component Tests', () => {
    describe('ItemFilme Management Update Component', () => {
        let comp: ItemFilmeUpdateComponent;
        let fixture: ComponentFixture<ItemFilmeUpdateComponent>;
        let service: ItemFilmeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VlimperialTestModule],
                declarations: [ItemFilmeUpdateComponent]
            })
                .overrideTemplate(ItemFilmeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemFilmeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemFilmeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemFilme(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemFilme = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ItemFilme();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.itemFilme = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
