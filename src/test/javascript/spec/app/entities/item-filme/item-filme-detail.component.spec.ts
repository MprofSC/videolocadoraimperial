/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VlimperialTestModule } from '../../../test.module';
import { ItemFilmeDetailComponent } from 'app/entities/item-filme/item-filme-detail.component';
import { ItemFilme } from 'app/shared/model/item-filme.model';

describe('Component Tests', () => {
    describe('ItemFilme Management Detail Component', () => {
        let comp: ItemFilmeDetailComponent;
        let fixture: ComponentFixture<ItemFilmeDetailComponent>;
        const route = ({ data: of({ itemFilme: new ItemFilme(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VlimperialTestModule],
                declarations: [ItemFilmeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemFilmeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemFilmeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemFilme).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
