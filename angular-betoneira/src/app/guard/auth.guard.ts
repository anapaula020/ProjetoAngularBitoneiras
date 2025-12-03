import { CanActivateFn,Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route,state) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if(authService.isTokenExpired()) {
        authService.removeToken();
        authService.removeUser();
        router.navigateByUrl("/login");
        return false;
    }

    const role = authService.getUserRole();
    
    if(role && route.data['role'] && route.data['role'] !== role) {
        router.navigateByUrl("/acesso-proibido");
        return false;
    }

    return true;
};
