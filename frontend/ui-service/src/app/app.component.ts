import {Component, inject} from "@angular/core";
import {RouterOutlet} from "@angular/router";
import {AsyncPipe} from "@angular/common";
import {AuthService} from "./auth.service";
import { DarkModeService } from "./services/darkmode.service";
import { FormsModule } from "@angular/forms";

@Component({
    selector: "app-root",
    standalone: true,
    imports: [RouterOutlet, AsyncPipe, FormsModule],
    templateUrl: "./app.component.html",
    styleUrl: "./app.component.css",
})
export class AppComponent {
    title = "online-ide";
    authService = inject(AuthService);
    darkModeService = inject(DarkModeService);

    constructor() {
        this.darkModeService.getDarkMode()
    }
}
