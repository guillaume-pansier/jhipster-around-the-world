import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CountryPathComponent } from './country-path.component';
import { CountryPathDetailComponent } from './country-path-detail.component';
import { CountryPathPopupComponent } from './country-path-dialog.component';
import { CountryPathDeletePopupComponent } from './country-path-delete-dialog.component';

export const countryPathRoute: Routes = [
    {
        path: 'country-path',
        component: CountryPathComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryPaths'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'country-path/:id',
        component: CountryPathDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryPaths'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countryPathPopupRoute: Routes = [
    {
        path: 'country-path-new',
        component: CountryPathPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryPaths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'country-path/:id/edit',
        component: CountryPathPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryPaths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'country-path/:id/delete',
        component: CountryPathDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryPaths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
