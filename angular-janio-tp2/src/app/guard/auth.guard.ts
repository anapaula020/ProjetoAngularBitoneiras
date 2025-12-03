import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  UrlTree,
} from '@angular/router';
import { AuthService } from '../services/auth.service';

function getLeafRoute(route: ActivatedRouteSnapshot): ActivatedRouteSnapshot {
  let leaf = route;

  while (leaf.firstChild) {
    leaf = leaf.firstChild;
  }

  return leaf;
}

export const authGuard: CanActivateFn = (
  route,
  state
): boolean | UrlTree => {

  const authService = inject(AuthService);
  const router = inject(Router);

  const snapshot = route as ActivatedRouteSnapshot;
  // obtendo a rota filha
  const leaf = getLeafRoute(snapshot);

  //  ex.: {path: 'login', component: LoginComponent, title: 'Login', data: {public: true}},
  const isPublic = leaf.data?.['public'] === true;

  if (isPublic) {
    return true;
  }

  const token = authService.getToken();
  const tokenExpired = !token || authService.isTokenExpired();

  if (tokenExpired) {
    authService.logout();

    return router.createUrlTree(['/login'], {
      queryParams: { returnUrl: state.url },
    });
  }

  return true;
};
