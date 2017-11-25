import { BaseEntity } from './../../shared';

export class Country implements BaseEntity {
    constructor(
        public id?: string,
        public countryName?: string,
        public isoCode?: string,
        public pathSvgFormat?: string,
    ) {
    }
}
