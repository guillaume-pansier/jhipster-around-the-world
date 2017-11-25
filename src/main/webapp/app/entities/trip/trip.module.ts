import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AroundTheWorldSharedModule } from '../../shared';
import {
    TripService,
    TripPopupService,
    TripComponent,
    TripDetailComponent,
    TripDialogComponent,
    TripPopupComponent,
    TripDeletePopupComponent,
    TripDeleteDialogComponent,
    tripRoute,
    tripPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tripRoute,
    ...tripPopupRoute,
];

@NgModule({
    imports: [
        AroundTheWorldSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TripComponent,
        TripDetailComponent,
        TripDialogComponent,
        TripDeleteDialogComponent,
        TripPopupComponent,
        TripDeletePopupComponent,
    ],
    entryComponents: [
        TripComponent,
        TripDialogComponent,
        TripPopupComponent,
        TripDeleteDialogComponent,
        TripDeletePopupComponent,
    ],
    providers: [
        TripService,
        TripPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AroundTheWorldTripModule {}
