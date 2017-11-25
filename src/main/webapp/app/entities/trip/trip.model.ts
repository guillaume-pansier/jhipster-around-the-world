import { BaseEntity } from './../../shared';

export const enum TripStatus {
    'DONE',
    'PLANNED'
}

export class Trip implements BaseEntity {
    constructor(
        public id?: string,
        public tripName?: string,
        public status?: TripStatus,
        public tripPathId?: string,
        public countryPaths?: string,
    ) {
    }
}
