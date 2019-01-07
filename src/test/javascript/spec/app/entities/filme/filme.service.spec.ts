/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { FilmeService } from 'app/entities/filme/filme.service';
import { IFilme, Filme, Genero } from 'app/shared/model/filme.model';

describe('Service Tests', () => {
    describe('Filme Service', () => {
        let injector: TestBed;
        let service: FilmeService;
        let httpMock: HttpTestingController;
        let elemDefault: IFilme;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(FilmeService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Filme(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', Genero.ACAO);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Filme', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Filme(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Filme', async () => {
                const returnedFromService = Object.assign(
                    {
                        tituloOriginal: 'BBBBBB',
                        tituloPortugues: 'BBBBBB',
                        paises: 'BBBBBB',
                        ano: 1,
                        direcao: 'BBBBBB',
                        elenco: 'BBBBBB',
                        sinopse: 'BBBBBB',
                        duracao: 'BBBBBB',
                        genero: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Filme', async () => {
                const returnedFromService = Object.assign(
                    {
                        tituloOriginal: 'BBBBBB',
                        tituloPortugues: 'BBBBBB',
                        paises: 'BBBBBB',
                        ano: 1,
                        direcao: 'BBBBBB',
                        elenco: 'BBBBBB',
                        sinopse: 'BBBBBB',
                        duracao: 'BBBBBB',
                        genero: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Filme', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
