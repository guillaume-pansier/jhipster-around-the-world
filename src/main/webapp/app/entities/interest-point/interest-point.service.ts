import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { InterestPoint } from './interest-point.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InterestPointService {

    private resourceUrl = SERVER_API_URL + 'api/interest-points';

    constructor(private http: Http) { }

    create(interestPoint: InterestPoint): Observable<InterestPoint> {
        const copy = this.convert(interestPoint);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(interestPoint: InterestPoint): Observable<InterestPoint> {
        const copy = this.convert(interestPoint);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<InterestPoint> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to InterestPoint.
     */
    private convertItemFromServer(json: any): InterestPoint {
        const entity: InterestPoint = Object.assign(new InterestPoint(), json);
        return entity;
    }

    /**
     * Convert a InterestPoint to a JSON which can be sent to the server.
     */
    private convert(interestPoint: InterestPoint): InterestPoint {
        const copy: InterestPoint = Object.assign({}, interestPoint);
        return copy;
    }
}
