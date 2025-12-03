import { ResolveFn } from '@angular/router';
import { Aluno } from '../models/aluno.model';
import { inject } from '@angular/core';
import { AlunoService } from '../services/aluno.service';

export const alunoResolver: ResolveFn<Aluno> = (route, state) => {
  return inject(AlunoService).buscarPorId(route.paramMap.get('id')!);
};
