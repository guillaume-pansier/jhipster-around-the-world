import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CountryPath } from './country-path.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CountryPathService {

    private resourceUrl = SERVER_API_URL + 'api/country-paths';

    constructor(private http: Http) { }

    create(countryPath: CountryPath): Observable<CountryPath> {
        const copy = this.convert(countryPath);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(countryPath: CountryPath): Observable<CountryPath> {
        const copy = this.convert(countryPath);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<CountryPath> {
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
     * Convert a returned JSON object to CountryPath.
     */
    private convertItemFromServer(json: any): CountryPath {
        const entity: CountryPath = Object.assign(new CountryPath(), json);
        return entity;
    }

    /**
     * Convert a CountryPath to a JSON which can be sent to the server.
     */
    private convert(countryPath: CountryPath): CountryPath {
        const copy: CountryPath = Object.assign({}, countryPath);
        return copy;
    }
}
