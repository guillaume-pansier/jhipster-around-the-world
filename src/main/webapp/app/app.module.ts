import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { AroundTheWorldSharedModule, UserRouteAccessService } from './shared';
import { AroundTheWorldHomeModule } from './home/home.module';
import { AroundTheWorldHomeNavModule } from './homeNav/home-nav.module';
import { AroundTheWorldAdminModule } from './admin/admin.module';
import { AroundTheWorldAccountModule } from './account/account.module';
import { AroundTheWorldEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { NguiMapModule} from '@ngui/map';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        AroundTheWorldSharedModule,
        AroundTheWorldHomeModule,
        AroundTheWorldAdminModule,
        AroundTheWorldHomeNavModule,
        AroundTheWorldAccountModule,
        AroundTheWorldEntityModule,
        NguiMapModule.forRoot({apiUrl: 'https://maps.google.com/maps/api/js?key=AIzaSyCbfov46Hk19QUCjXheiaFNkYn3k0id6Jc'}),
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class AroundTheWorldAppModule {}
