import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CountryPath } from './country-path.model';
import { CountryPathPopupService } from './country-path-popup.service';
import { CountryPathService } from './country-path.service';

@Component({
    selector: 'jhi-country-path-dialog',
    templateUrl: './country-path-dialog.component.html'
})
export class CountryPathDialogComponent implements OnInit {

    countryPath: CountryPath;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private countryPathService: CountryPathService,
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
        if (this.countryPath.id !== undefined) {
            this.subscribeToSaveResponse(
                this.countryPathService.update(this.countryPath));
        } else {
            this.subscribeToSaveResponse(
                this.countryPathService.create(this.countryPath));
        }
    }

    private subscribeToSaveResponse(result: Observable<CountryPath>) {
        result.subscribe((res: CountryPath) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CountryPath) {
        this.eventManager.broadcast({ name: 'countryPathListModification', content: 'OK'});
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
    selector: 'jhi-country-path-popup',
    template: ''
})
export class CountryPathPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countryPathPopupService: CountryPathPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.countryPathPopupService
                    .open(CountryPathDialogComponent as Component, params['id']);
            } else {
                this.countryPathPopupService
                    .open(CountryPathDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
