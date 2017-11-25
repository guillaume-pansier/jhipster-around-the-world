import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CountryPath } from './country-path.model';
import { CountryPathService } from './country-path.service';

@Component({
    selector: 'jhi-country-path-detail',
    templateUrl: './country-path-detail.component.html'
})
export class CountryPathDetailComponent implements OnInit, OnDestroy {

    countryPath: CountryPath;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private countryPathService: CountryPathService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCountryPaths();
    }

    load(id) {
        this.countryPathService.find(id).subscribe((countryPath) => {
            this.countryPath = countryPath;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCountryPaths() {
        this.eventSubscriber = this.eventManager.subscribe(
            'countryPathListModification',
            (response) => this.load(this.countryPath.id)
        );
    }
}
