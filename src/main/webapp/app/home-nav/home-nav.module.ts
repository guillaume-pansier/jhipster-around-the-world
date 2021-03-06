import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NguiMapModule} from '@ngui/map';

import { AroundTheWorldSharedModule } from '../shared';

import { HOME_NAV_ROUTE, HomeNavComponent } from './';

@NgModule({
    imports: [
        AroundTheWorldSharedModule,
        RouterModule.forRoot([ HOME_NAV_ROUTE ], { useHash: true }),
        NguiMapModule.forRoot({apiUrl: 'https://maps.google.com/maps/api/js?key=AIzaSyCbfov46Hk19QUCjXheiaFNkYn3k0id6Jc'})
    ],
    declarations: [
        HomeNavComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AroundTheWorldHomeNavModule {}
