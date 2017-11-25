/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AroundTheWorldTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CountryPathDetailComponent } from '../../../../../../main/webapp/app/entities/country-path/country-path-detail.component';
import { CountryPathService } from '../../../../../../main/webapp/app/entities/country-path/country-path.service';
import { CountryPath } from '../../../../../../main/webapp/app/entities/country-path/country-path.model';

describe('Component Tests', () => {

    describe('CountryPath Management Detail Component', () => {
        let comp: CountryPathDetailComponent;
        let fixture: ComponentFixture<CountryPathDetailComponent>;
        let service: CountryPathService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AroundTheWorldTestModule],
                declarations: [CountryPathDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CountryPathService,
                    JhiEventManager
                ]
            }).overrideTemplate(CountryPathDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CountryPathDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountryPathService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CountryPath('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.countryPath).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
