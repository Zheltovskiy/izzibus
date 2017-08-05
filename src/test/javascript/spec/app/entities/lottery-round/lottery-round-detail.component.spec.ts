/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IzzibusTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LotteryRoundDetailComponent } from '../../../../../../main/webapp/app/entities/lottery-round/lottery-round-detail.component';
import { LotteryRoundService } from '../../../../../../main/webapp/app/entities/lottery-round/lottery-round.service';
import { LotteryRound } from '../../../../../../main/webapp/app/entities/lottery-round/lottery-round.model';

describe('Component Tests', () => {

    describe('LotteryRound Management Detail Component', () => {
        let comp: LotteryRoundDetailComponent;
        let fixture: ComponentFixture<LotteryRoundDetailComponent>;
        let service: LotteryRoundService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IzzibusTestModule],
                declarations: [LotteryRoundDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LotteryRoundService,
                    JhiEventManager
                ]
            }).overrideTemplate(LotteryRoundDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LotteryRoundDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LotteryRoundService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LotteryRound(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lotteryRound).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
