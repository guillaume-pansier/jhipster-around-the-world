import { BaseEntity } from './../../shared';

export class CountryPath implements BaseEntity {
    constructor(
        public id?: string,
        public isoCode?: string,
        public interestPoints?: string,
    ) {
    }
}
