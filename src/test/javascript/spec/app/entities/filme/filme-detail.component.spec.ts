/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VlimperialTestModule } from '../../../test.module';
import { FilmeDetailComponent } from 'app/entities/filme/filme-detail.component';
import { Filme } from 'app/shared/model/filme.model';

describe('Component Tests', () => {
    describe('Filme Management Detail Component', () => {
        let comp: FilmeDetailComponent;
        let fixture: ComponentFixture<FilmeDetailComponent>;
        const route = ({ data: of({ filme: new Filme(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VlimperialTestModule],
                declarations: [FilmeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FilmeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FilmeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.filme).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
