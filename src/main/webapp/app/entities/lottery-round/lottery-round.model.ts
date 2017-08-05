import { BaseEntity } from './../../shared';

export class LotteryRound implements BaseEntity {
    constructor(
        public id?: number,
        public datetime?: any,
        public winner?: BaseEntity,
    ) {
    }
}
