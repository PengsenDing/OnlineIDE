import { inject, Injectable } from '@angular/core';
import { CanActivate, GuardResult, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { first, firstValueFrom, map, withLatestFrom } from 'rxjs';

@Injectable({ providedIn: "root" })
export class AuthGuard implements CanActivate {

    authService = inject(AuthService);
    router = inject(Router);

    async canActivate(): Promise<GuardResult> {
        const isAuthenticated = await firstValueFrom(
            this.authService.checked.pipe(
                // Wait for auth service to have checked if already logged in
                first(isChecked => isChecked),
                withLatestFrom(this.authService.authenticated),
                map(([_, isAuthenticated]) => isAuthenticated)
            )
        );
        if (!isAuthenticated) {
            return this.router.parseUrl('/error');
        }
        return isAuthenticated;
    }

}
