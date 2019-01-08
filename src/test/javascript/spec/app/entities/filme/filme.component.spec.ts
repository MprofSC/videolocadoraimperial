/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VlimperialTestModule } from '../../../test.module';
import { FilmeComponent } from 'app/entities/filme/filme.component';
import { FilmeService } from 'app/entities/filme/filme.service';
import { Filme } from 'app/shared/model/filme.model';

describe('Component Tests', () => {
    describe('Filme Management Component', () => {
        let comp: FilmeComponent;
        let fixture: ComponentFixture<FilmeComponent>;
        let service: FilmeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VlimperialTestModule],
                declarations: [FilmeComponent],
                providers: []
            })
                .overrideTemplate(FilmeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FilmeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilmeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Filme(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.filmes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
