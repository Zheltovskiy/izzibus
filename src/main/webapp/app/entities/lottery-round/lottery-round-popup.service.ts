import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { LotteryRound } from './lottery-round.model';
import { LotteryRoundService } from './lottery-round.service';

@Injectable()
export class LotteryRoundPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private lotteryRoundService: LotteryRoundService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.lotteryRoundService.find(id).subscribe((lotteryRound) => {
                lotteryRound.datetime = this.datePipe
                    .transform(lotteryRound.datetime, 'yyyy-MM-ddThh:mm');
                this.lotteryRoundModalRef(component, lotteryRound);
            });
        } else {
            return this.lotteryRoundModalRef(component, new LotteryRound());
        }
    }

    lotteryRoundModalRef(component: Component, lotteryRound: LotteryRound): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lotteryRound = lotteryRound;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
