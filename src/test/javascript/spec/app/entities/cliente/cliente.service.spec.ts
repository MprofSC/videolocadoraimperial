/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { ICliente, Cliente, GeneroPessoa } from 'app/shared/model/cliente.model';

describe('Service Tests', () => {
    describe('Cliente Service', () => {
        let injector: TestBed;
        let service: ClienteService;
        let httpMock: HttpTestingController;
        let elemDefault: ICliente;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ClienteService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Cliente(
                0,
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                GeneroPessoa.MASCULINO,
                currentDate,
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataNascimento: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Cliente', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataNascimento: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataNascimento: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Cliente(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Cliente', async () => {
                const returnedFromService = Object.assign(
                    {
                        numeroIncricao: 1,
                        nome: 'BBBBBB',
                        cpf: 1,
                        email: 'BBBBBB',
                        endereco: 'BBBBBB',
                        telefoneResidencial: 'BBBBBB',
                        telefoneComercial: 'BBBBBB',
                        telefoneCelular: 'BBBBBB',
                        localTrabalho: 'BBBBBB',
                        sexo: 'BBBBBB',
                        dataNascimento: currentDate.format(DATE_TIME_FORMAT),
                        ativo: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataNascimento: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Cliente', async () => {
                const returnedFromService = Object.assign(
                    {
                        numeroIncricao: 1,
                        nome: 'BBBBBB',
                        cpf: 1,
                        email: 'BBBBBB',
                        endereco: 'BBBBBB',
                        telefoneResidencial: 'BBBBBB',
                        telefoneComercial: 'BBBBBB',
                        telefoneCelular: 'BBBBBB',
                        localTrabalho: 'BBBBBB',
                        sexo: 'BBBBBB',
                        dataNascimento: currentDate.format(DATE_TIME_FORMAT),
                        ativo: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataNascimento: currentDate
                    },
                    returnedFromService
                );
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

            it('should delete a Cliente', async () => {
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
