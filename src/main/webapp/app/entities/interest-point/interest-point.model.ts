import { BaseEntity } from './../../shared';

export class InterestPoint implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public coordinates?: string,
    ) {
    }
}
