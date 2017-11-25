import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CountryPath } from './country-path.model';
import { CountryPathPopupService } from './country-path-popup.service';
import { CountryPathService } from './country-path.service';

@Component({
    selector: 'jhi-country-path-delete-dialog',
    templateUrl: './country-path-delete-dialog.component.html'
})
export class CountryPathDeleteDialogComponent {

    countryPath: CountryPath;

    constructor(
        private countryPathService: CountryPathService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.countryPathService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'countryPathListModification',
                content: 'Deleted an countryPath'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-country-path-delete-popup',
    template: ''
})
export class CountryPathDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countryPathPopupService: CountryPathPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.countryPathPopupService
                .open(CountryPathDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
