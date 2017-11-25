import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AroundTheWorldSharedModule } from '../../shared';
import {
    InterestPointService,
    InterestPointPopupService,
    InterestPointComponent,
    InterestPointDetailComponent,
    InterestPointDialogComponent,
    InterestPointPopupComponent,
    InterestPointDeletePopupComponent,
    InterestPointDeleteDialogComponent,
    interestPointRoute,
    interestPointPopupRoute,
} from './';

const ENTITY_STATES = [
    ...interestPointRoute,
    ...interestPointPopupRoute,
];

@NgModule({
    imports: [
        AroundTheWorldSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InterestPointComponent,
        InterestPointDetailComponent,
        InterestPointDialogComponent,
        InterestPointDeleteDialogComponent,
        InterestPointPopupComponent,
        InterestPointDeletePopupComponent,
    ],
    entryComponents: [
        InterestPointComponent,
        InterestPointDialogComponent,
        InterestPointPopupComponent,
        InterestPointDeleteDialogComponent,
        InterestPointDeletePopupComponent,
    ],
    providers: [
        InterestPointService,
        InterestPointPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AroundTheWorldInterestPointModule {}
