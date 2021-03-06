import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { LotteryRound } from './lottery-round.model';
import { LotteryRoundService } from './lottery-round.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-lottery-round',
    templateUrl: './lottery-round.component.html'
})
export class LotteryRoundComponent implements OnInit, OnDestroy {
lotteryRounds: LotteryRound[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private lotteryRoundService: LotteryRoundService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.lotteryRoundService.query().subscribe(
            (res: ResponseWrapper) => {
                this.lotteryRounds = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLotteryRounds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LotteryRound) {
        return item.id;
    }
    registerChangeInLotteryRounds() {
        this.eventSubscriber = this.eventManager.subscribe('lotteryRoundListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
