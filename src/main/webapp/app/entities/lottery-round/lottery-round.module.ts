import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IzzibusSharedModule } from '../../shared';
import {
    LotteryRoundService,
    LotteryRoundPopupService,
    LotteryRoundComponent,
    LotteryRoundDetailComponent,
    LotteryRoundDialogComponent,
    LotteryRoundPopupComponent,
    LotteryRoundDeletePopupComponent,
    LotteryRoundDeleteDialogComponent,
    lotteryRoundRoute,
    lotteryRoundPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lotteryRoundRoute,
    ...lotteryRoundPopupRoute,
];

@NgModule({
    imports: [
        IzzibusSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LotteryRoundComponent,
        LotteryRoundDetailComponent,
        LotteryRoundDialogComponent,
        LotteryRoundDeleteDialogComponent,
        LotteryRoundPopupComponent,
        LotteryRoundDeletePopupComponent,
    ],
    entryComponents: [
        LotteryRoundComponent,
        LotteryRoundDialogComponent,
        LotteryRoundPopupComponent,
        LotteryRoundDeleteDialogComponent,
        LotteryRoundDeletePopupComponent,
    ],
    providers: [
        LotteryRoundService,
        LotteryRoundPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IzzibusLotteryRoundModule {}
