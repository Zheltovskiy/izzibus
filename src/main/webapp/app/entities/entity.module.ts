import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { IzzibusTicketModule } from './ticket/ticket.module';
import { IzzibusLotteryRoundModule } from './lottery-round/lottery-round.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        IzzibusTicketModule,
        IzzibusLotteryRoundModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IzzibusEntityModule {}
