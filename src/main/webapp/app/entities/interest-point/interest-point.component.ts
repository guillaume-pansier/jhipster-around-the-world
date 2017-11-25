import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { InterestPoint } from './interest-point.model';
import { InterestPointService } from './interest-point.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-interest-point',
    templateUrl: './interest-point.component.html'
})
export class InterestPointComponent implements OnInit, OnDestroy {
interestPoints: InterestPoint[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private interestPointService: InterestPointService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.interestPointService.query().subscribe(
            (res: ResponseWrapper) => {
                this.interestPoints = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInterestPoints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InterestPoint) {
        return item.id;
    }
    registerChangeInInterestPoints() {
        this.eventSubscriber = this.eventManager.subscribe('interestPointListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
