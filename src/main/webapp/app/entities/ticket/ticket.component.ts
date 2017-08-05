import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { Ticket } from './ticket.model';
import { TicketService } from './ticket.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ticket',
    templateUrl: './ticket.component.html'
})
export class TicketComponent implements OnInit, OnDestroy {
tickets: Ticket[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ticketService: TicketService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ticketService.query().subscribe(
            (res: ResponseWrapper) => {
                this.tickets = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTickets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Ticket) {
        return item.id;
    }
    registerChangeInTickets() {
        this.eventSubscriber = this.eventManager.subscribe('ticketListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
