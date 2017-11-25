import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Trip } from './trip.model';
import { TripPopupService } from './trip-popup.service';
import { TripService } from './trip.service';

@Component({
    selector: 'jhi-trip-dialog',
    templateUrl: './trip-dialog.component.html'
})
export class TripDialogComponent implements OnInit {

    trip: Trip;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tripService: TripService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.trip.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tripService.update(this.trip));
        } else {
            this.subscribeToSaveResponse(
                this.tripService.create(this.trip));
        }
    }

    private subscribeToSaveResponse(result: Observable<Trip>) {
        result.subscribe((res: Trip) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Trip) {
        this.eventManager.broadcast({ name: 'tripListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-trip-popup',
    template: ''
})
export class TripPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tripPopupService: TripPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tripPopupService
                    .open(TripDialogComponent as Component, params['id']);
            } else {
                this.tripPopupService
                    .open(TripDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
