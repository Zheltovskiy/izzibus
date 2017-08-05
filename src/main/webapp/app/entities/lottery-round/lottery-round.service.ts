import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { LotteryRound } from './lottery-round.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LotteryRoundService {

    private resourceUrl = 'api/lottery-rounds';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(lotteryRound: LotteryRound): Observable<LotteryRound> {
        const copy = this.convert(lotteryRound);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(lotteryRound: LotteryRound): Observable<LotteryRound> {
        const copy = this.convert(lotteryRound);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<LotteryRound> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.datetime = this.dateUtils
            .convertDateTimeFromServer(entity.datetime);
    }

    private convert(lotteryRound: LotteryRound): LotteryRound {
        const copy: LotteryRound = Object.assign({}, lotteryRound);

        copy.datetime = this.dateUtils.toDate(lotteryRound.datetime);
        return copy;
    }
}
