import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AroundTheWorldInterestPointModule } from './interest-point/interest-point.module';
import { AroundTheWorldCountryModule } from './country/country.module';
import { AroundTheWorldCountryPathModule } from './country-path/country-path.module';
import { AroundTheWorldTripModule } from './trip/trip.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AroundTheWorldInterestPointModule,
        AroundTheWorldCountryModule,
        AroundTheWorldCountryPathModule,
        AroundTheWorldTripModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AroundTheWorldEntityModule {}
