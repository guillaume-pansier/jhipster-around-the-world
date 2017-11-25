import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { CountryPath } from './country-path.model';
import { CountryPathService } from './country-path.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-country-path',
    templateUrl: './country-path.component.html'
})
export class CountryPathComponent implements OnInit, OnDestroy {
countryPaths: CountryPath[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private countryPathService: CountryPathService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.countryPathService.query().subscribe(
            (res: ResponseWrapper) => {
                this.countryPaths = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCountryPaths();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CountryPath) {
        return item.id;
    }
    registerChangeInCountryPaths() {
        this.eventSubscriber = this.eventManager.subscribe('countryPathListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
