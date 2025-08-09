import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, of, tap} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private baseUrl: String = "/backend"

    private authenticatedSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private checkedSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    authenticated: Observable<boolean> = this.authenticatedSubject.asObservable();
    checked: Observable<boolean> = this.checkedSubject.asObservable();

    constructor(private http: HttpClient) {
        this.checkAuthenticated()
    }

    checkAuthenticated(): void {
        this.http.get<boolean>(`${this.baseUrl}/authentication`)
            .pipe(
                tap(response => {
                    this.authenticatedSubject.next(response);
                    this.checkedSubject.next(true);
                }),
                catchError(() => {
                    this.authenticatedSubject.next(false);
                    this.checkedSubject.next(true);
                    return of();
                })
            )
            .subscribe();
    }

    login() {
        window.location.href = "/login"
    }

    logout() {
        window.location.href = "/logout"
    }
}
