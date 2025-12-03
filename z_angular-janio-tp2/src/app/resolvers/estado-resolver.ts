import { ResolveFn } from '@angular/router';
import { Estado } from '../models/estado.model';
import { inject } from '@angular/core';
import { EstadoService } from '../services/estado.service';

export const estadoResolver: ResolveFn<Estado> = (route, state) => {
  return inject(EstadoService).buscarPorId(route.paramMap.get('id')!);
};
