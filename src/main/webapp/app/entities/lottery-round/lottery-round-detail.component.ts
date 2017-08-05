import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { LotteryRound } from './lottery-round.model';
import { LotteryRoundService } from './lottery-round.service';

@Component({
    selector: 'jhi-lottery-round-detail',
    templateUrl: './lottery-round-detail.component.html'
})
export class LotteryRoundDetailComponent implements OnInit, OnDestroy {

    lotteryRound: LotteryRound;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lotteryRoundService: LotteryRoundService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLotteryRounds();
    }

    load(id) {
        this.lotteryRoundService.find(id).subscribe((lotteryRound) => {
            this.lotteryRound = lotteryRound;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLotteryRounds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lotteryRoundListModification',
            (response) => this.load(this.lotteryRound.id)
        );
    }
}
