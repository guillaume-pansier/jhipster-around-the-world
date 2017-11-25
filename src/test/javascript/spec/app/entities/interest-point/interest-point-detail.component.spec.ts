/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AroundTheWorldTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InterestPointDetailComponent } from '../../../../../../main/webapp/app/entities/interest-point/interest-point-detail.component';
import { InterestPointService } from '../../../../../../main/webapp/app/entities/interest-point/interest-point.service';
import { InterestPoint } from '../../../../../../main/webapp/app/entities/interest-point/interest-point.model';

describe('Component Tests', () => {

    describe('InterestPoint Management Detail Component', () => {
        let comp: InterestPointDetailComponent;
        let fixture: ComponentFixture<InterestPointDetailComponent>;
        let service: InterestPointService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AroundTheWorldTestModule],
                declarations: [InterestPointDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InterestPointService,
                    JhiEventManager
                ]
            }).overrideTemplate(InterestPointDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InterestPointDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InterestPointService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new InterestPoint('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.interestPoint).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
