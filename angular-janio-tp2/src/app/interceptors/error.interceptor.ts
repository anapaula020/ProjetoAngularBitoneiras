import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const errorInterceptor: HttpInterceptorFn = (request, next) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      // 401 / 403 -> problemas de autenticacao/autorizacao
      if (error.status === 401 || error.status === 403) {
        // limpa sessao
        authService.logout?.();

        const currentUrl = router.url;

        // evita redirecionar em loop se estiver em /login
        if (!currentUrl.startsWith('/login')) {
          router.navigate(['/login'], {
            queryParams: { returnUrl: currentUrl },
          });
        }
      } 
      // 5xx - erro de servidor
      else if (error.status >= 500) {
        console.error('Erro no servidor (5xx):', error);
      } 
      // 0 - erro de rede / CORS / servidor fora
      else if (error.status === 0) {
        console.error('Erro de rede ou servidor indisponível:', error);
      } 
      // 4xx genéricos (400, 404, 422, etc.)
      else if (error.status >= 400) {
        console.warn('Erro de requisição (4xx):', error);
      }

      // repropaga o erro pra quem chamou
      return throwError(() => error);
    })
  );
};
