import { Route } from '@angular/router';

import { HomeNavComponent } from './';

export const HOME_NAV_ROUTE: Route = {
    path: '',
    outlet: 'other',
    component: HomeNavComponent,
    data: {
        authorities: [],
        pageTitle: 'Welcome, Java Hipster!'
    }
};
