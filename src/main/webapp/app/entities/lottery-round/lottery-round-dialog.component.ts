import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LotteryRound } from './lottery-round.model';
import { LotteryRoundPopupService } from './lottery-round-popup.service';
import { LotteryRoundService } from './lottery-round.service';
import { Ticket, TicketService } from '../ticket';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lottery-round-dialog',
    templateUrl: './lottery-round-dialog.component.html'
})
export class LotteryRoundDialogComponent implements OnInit {

    lotteryRound: LotteryRound;
    authorities: any[];
    isSaving: boolean;

    winners: Ticket[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private lotteryRoundService: LotteryRoundService,
        private ticketService: TicketService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.ticketService
            .query({filter: 'winninground-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.lotteryRound.winner || !this.lotteryRound.winner.id) {
                    this.winners = res.json;
                } else {
                    this.ticketService
                        .find(this.lotteryRound.winner.id)
                        .subscribe((subRes: Ticket) => {
                            this.winners = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lotteryRound.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lotteryRoundService.update(this.lotteryRound));
        } else {
            this.subscribeToSaveResponse(
                this.lotteryRoundService.create(this.lotteryRound));
        }
    }

    private subscribeToSaveResponse(result: Observable<LotteryRound>) {
        result.subscribe((res: LotteryRound) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: LotteryRound) {
        this.eventManager.broadcast({ name: 'lotteryRoundListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackTicketById(index: number, item: Ticket) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-lottery-round-popup',
    template: ''
})
export class LotteryRoundPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lotteryRoundPopupService: LotteryRoundPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.lotteryRoundPopupService
                    .open(LotteryRoundDialogComponent, params['id']);
            } else {
                this.modalRef = this.lotteryRoundPopupService
                    .open(LotteryRoundDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
