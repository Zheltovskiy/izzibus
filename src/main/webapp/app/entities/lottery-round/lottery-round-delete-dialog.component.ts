import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LotteryRound } from './lottery-round.model';
import { LotteryRoundPopupService } from './lottery-round-popup.service';
import { LotteryRoundService } from './lottery-round.service';

@Component({
    selector: 'jhi-lottery-round-delete-dialog',
    templateUrl: './lottery-round-delete-dialog.component.html'
})
export class LotteryRoundDeleteDialogComponent {

    lotteryRound: LotteryRound;

    constructor(
        private lotteryRoundService: LotteryRoundService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lotteryRoundService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lotteryRoundListModification',
                content: 'Deleted an lotteryRound'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lottery-round-delete-popup',
    template: ''
})
export class LotteryRoundDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lotteryRoundPopupService: LotteryRoundPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.lotteryRoundPopupService
                .open(LotteryRoundDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
