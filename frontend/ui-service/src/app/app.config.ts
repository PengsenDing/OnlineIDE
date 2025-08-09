import {
    ApplicationConfig,
    importProvidersFrom,
    provideZoneChangeDetection,
} from "@angular/core";
import {provideRouter} from "@angular/router";

import {routes} from "./app.routes";
import {MonacoEditorModule} from "ngx-monaco-editor-v2";
import {ProjectService} from "./services/project.service";
import {
    provideHttpClient,
    withInterceptorsFromDi,
} from "@angular/common/http";
import {AuthService} from "./auth.service";
import { DarkModeService } from "./services/darkmode.service";
import { CompilerService } from "./services/compiler.service";

export const appConfig: ApplicationConfig = {
    providers: [
        provideZoneChangeDetection({eventCoalescing: true}),
        provideRouter(routes),
        importProvidersFrom(MonacoEditorModule.forRoot()),
        provideHttpClient(withInterceptorsFromDi()),
        ProjectService,
        AuthService,
        DarkModeService,
        CompilerService
    ],
};
