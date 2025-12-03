import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (request, next) => {

   const authService = inject(AuthService);
   const token = authService.getToken();

  // 1) Se ja existe Authorization, deixa seguir sem mexer
  if (request.headers.has('Authorization')) {
    return next(request);
  }

  // 2) Se nao tem token ou esta expirado, deixa seguir sem mexer
  if (!token || authService.isTokenExpired()) {
    return next(request);
  }

  // 3) Se for chamada para o endpoint de login/auth, deixa seguir sem mexer
  if (request.url.includes('/auth')) {
    return next(request);
  }

  // 4) Clona requisicao com Authorization: Bearer <token>
  const authRequest = request.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  return next(authRequest);
};
