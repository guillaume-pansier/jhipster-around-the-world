import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InterestPoint } from './interest-point.model';
import { InterestPointPopupService } from './interest-point-popup.service';
import { InterestPointService } from './interest-point.service';

@Component({
    selector: 'jhi-interest-point-dialog',
    templateUrl: './interest-point-dialog.component.html'
})
export class InterestPointDialogComponent implements OnInit {

    interestPoint: InterestPoint;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private interestPointService: InterestPointService,
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
        if (this.interestPoint.id !== undefined) {
            this.subscribeToSaveResponse(
                this.interestPointService.update(this.interestPoint));
        } else {
            this.subscribeToSaveResponse(
                this.interestPointService.create(this.interestPoint));
        }
    }

    private subscribeToSaveResponse(result: Observable<InterestPoint>) {
        result.subscribe((res: InterestPoint) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: InterestPoint) {
        this.eventManager.broadcast({ name: 'interestPointListModification', content: 'OK'});
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
    selector: 'jhi-interest-point-popup',
    template: ''
})
export class InterestPointPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private interestPointPopupService: InterestPointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.interestPointPopupService
                    .open(InterestPointDialogComponent as Component, params['id']);
            } else {
                this.interestPointPopupService
                    .open(InterestPointDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
