import { BaseEntity } from './../../shared';

export class Ticket implements BaseEntity {
    constructor(
        public id?: number,
        public phoneNumber?: string,
        public valid?: boolean,
        public ticket_id?: string,
    ) {
        this.valid = false;
    }
}
