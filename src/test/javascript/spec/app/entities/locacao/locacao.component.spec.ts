/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VlimperialTestModule } from '../../../test.module';
import { LocacaoComponent } from 'app/entities/locacao/locacao.component';
import { LocacaoService } from 'app/entities/locacao/locacao.service';
import { Locacao } from 'app/shared/model/locacao.model';

describe('Component Tests', () => {
    describe('Locacao Management Component', () => {
        let comp: LocacaoComponent;
        let fixture: ComponentFixture<LocacaoComponent>;
        let service: LocacaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VlimperialTestModule],
                declarations: [LocacaoComponent],
                providers: []
            })
                .overrideTemplate(LocacaoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocacaoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocacaoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Locacao(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.locacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
