import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InterestPoint } from './interest-point.model';
import { InterestPointPopupService } from './interest-point-popup.service';
import { InterestPointService } from './interest-point.service';

@Component({
    selector: 'jhi-interest-point-delete-dialog',
    templateUrl: './interest-point-delete-dialog.component.html'
})
export class InterestPointDeleteDialogComponent {

    interestPoint: InterestPoint;

    constructor(
        private interestPointService: InterestPointService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.interestPointService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'interestPointListModification',
                content: 'Deleted an interestPoint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-interest-point-delete-popup',
    template: ''
})
export class InterestPointDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private interestPointPopupService: InterestPointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.interestPointPopupService
                .open(InterestPointDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
