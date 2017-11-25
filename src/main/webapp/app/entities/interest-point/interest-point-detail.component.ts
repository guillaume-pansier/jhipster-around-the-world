import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { InterestPoint } from './interest-point.model';
import { InterestPointService } from './interest-point.service';

@Component({
    selector: 'jhi-interest-point-detail',
    templateUrl: './interest-point-detail.component.html'
})
export class InterestPointDetailComponent implements OnInit, OnDestroy {

    interestPoint: InterestPoint;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private interestPointService: InterestPointService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInterestPoints();
    }

    load(id) {
        this.interestPointService.find(id).subscribe((interestPoint) => {
            this.interestPoint = interestPoint;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInterestPoints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'interestPointListModification',
            (response) => this.load(this.interestPoint.id)
        );
    }
}
