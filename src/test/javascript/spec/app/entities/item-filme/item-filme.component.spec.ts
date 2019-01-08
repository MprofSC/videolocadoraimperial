/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VlimperialTestModule } from '../../../test.module';
import { ItemFilmeComponent } from 'app/entities/item-filme/item-filme.component';
import { ItemFilmeService } from 'app/entities/item-filme/item-filme.service';
import { ItemFilme } from 'app/shared/model/item-filme.model';

describe('Component Tests', () => {
    describe('ItemFilme Management Component', () => {
        let comp: ItemFilmeComponent;
        let fixture: ComponentFixture<ItemFilmeComponent>;
        let service: ItemFilmeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VlimperialTestModule],
                declarations: [ItemFilmeComponent],
                providers: []
            })
                .overrideTemplate(ItemFilmeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemFilmeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemFilmeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ItemFilme(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.itemFilmes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
