import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LotteryRoundComponent } from './lottery-round.component';
import { LotteryRoundDetailComponent } from './lottery-round-detail.component';
import { LotteryRoundPopupComponent } from './lottery-round-dialog.component';
import { LotteryRoundDeletePopupComponent } from './lottery-round-delete-dialog.component';

import { Principal } from '../../shared';

export const lotteryRoundRoute: Routes = [
    {
        path: 'lottery-round',
        component: LotteryRoundComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LotteryRounds'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lottery-round/:id',
        component: LotteryRoundDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LotteryRounds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lotteryRoundPopupRoute: Routes = [
    {
        path: 'lottery-round-new',
        component: LotteryRoundPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LotteryRounds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lottery-round/:id/edit',
        component: LotteryRoundPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LotteryRounds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lottery-round/:id/delete',
        component: LotteryRoundDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LotteryRounds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
