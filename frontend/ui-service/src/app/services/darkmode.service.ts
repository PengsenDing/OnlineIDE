import {effect, Inject, Injectable, signal, WritableSignal} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, tap} from "rxjs";
import { DOCUMENT } from "@angular/common";

@Injectable({providedIn: "root"})
export class DarkModeService {

    private baseUrl: string = "/darkmode/dark-mode"

    isDarkMode: WritableSignal<Boolean> = signal(false);

    constructor(private http: HttpClient, @Inject(DOCUMENT) private document: Document) {
        effect(() => {
            this.document.documentElement.setAttribute("data-bs-theme", `${this.isDarkMode() ? "dark" : ""}`)
        })
    }

    getDarkMode(): void {
        this.http.get<Boolean>(this.baseUrl)
            .pipe(
                tap(response => this.isDarkMode.set(response)),
                catchError(error => {
                    console.error("Oohps! Could not get darkmode state.");
                    throw error;
                })
            )
            .subscribe();
    }

    toggleDarkMode() {
        this.http.get<Boolean>(`${this.baseUrl}/toggle`)
            .pipe(
                tap(response => this.isDarkMode.set(response)),
                catchError(error => {
                    console.error("Ohhps! Could not set darkmode!");
                    throw error;
                })
            )
            .subscribe();
    }
}
