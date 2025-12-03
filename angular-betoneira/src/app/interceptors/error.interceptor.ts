import { HttpErrorResponse,HttpEvent,HttpHandler,HttpInterceptor,HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError,Observable,throwError } from 'rxjs';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private router: Router) { };

    intercept(request: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => {
                const listOfErrors = [401,403];
                if(listOfErrors.includes(error.status)) {
                    this.router.navigateByUrl("/login");
                }
                return throwError(() => error); 
            })
        )
    }
}