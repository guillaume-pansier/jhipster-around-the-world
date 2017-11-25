import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InterestPointComponent } from './interest-point.component';
import { InterestPointDetailComponent } from './interest-point-detail.component';
import { InterestPointPopupComponent } from './interest-point-dialog.component';
import { InterestPointDeletePopupComponent } from './interest-point-delete-dialog.component';

export const interestPointRoute: Routes = [
    {
        path: 'interest-point',
        component: InterestPointComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InterestPoints'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'interest-point/:id',
        component: InterestPointDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InterestPoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const interestPointPopupRoute: Routes = [
    {
        path: 'interest-point-new',
        component: InterestPointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InterestPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'interest-point/:id/edit',
        component: InterestPointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InterestPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'interest-point/:id/delete',
        component: InterestPointDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InterestPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
