/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VideoLocadoraImperialTestModule } from '../../../test.module';
import { FilmeUpdateComponent } from 'app/entities/filme/filme-update.component';
import { FilmeService } from 'app/entities/filme/filme.service';
import { Filme } from 'app/shared/model/filme.model';

describe('Component Tests', () => {
    describe('Filme Management Update Component', () => {
        let comp: FilmeUpdateComponent;
        let fixture: ComponentFixture<FilmeUpdateComponent>;
        let service: FilmeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VideoLocadoraImperialTestModule],
                declarations: [FilmeUpdateComponent]
            })
                .overrideTemplate(FilmeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FilmeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilmeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Filme(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.filme = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Filme();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.filme = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
