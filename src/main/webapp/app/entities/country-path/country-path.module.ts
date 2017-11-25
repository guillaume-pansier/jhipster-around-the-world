import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AroundTheWorldSharedModule } from '../../shared';
import {
    CountryPathService,
    CountryPathPopupService,
    CountryPathComponent,
    CountryPathDetailComponent,
    CountryPathDialogComponent,
    CountryPathPopupComponent,
    CountryPathDeletePopupComponent,
    CountryPathDeleteDialogComponent,
    countryPathRoute,
    countryPathPopupRoute,
} from './';

const ENTITY_STATES = [
    ...countryPathRoute,
    ...countryPathPopupRoute,
];

@NgModule({
    imports: [
        AroundTheWorldSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CountryPathComponent,
        CountryPathDetailComponent,
        CountryPathDialogComponent,
        CountryPathDeleteDialogComponent,
        CountryPathPopupComponent,
        CountryPathDeletePopupComponent,
    ],
    entryComponents: [
        CountryPathComponent,
        CountryPathDialogComponent,
        CountryPathPopupComponent,
        CountryPathDeleteDialogComponent,
        CountryPathDeletePopupComponent,
    ],
    providers: [
        CountryPathService,
        CountryPathPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AroundTheWorldCountryPathModule {}
