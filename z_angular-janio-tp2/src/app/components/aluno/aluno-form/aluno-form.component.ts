import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AlunoService } from '../../../services/aluno.service';
import { NumberFormatStyle } from '@angular/common';

@Component({
  selector: 'app-aluno-form',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],
  templateUrl: './aluno-form.component.html',
  styleUrl: './aluno-form.component.css'
})
export class AlunoFormComponent implements OnInit {

  readonly form;

  constructor(private fb: FormBuilder,
    private snack: MatSnackBar,
    private alunoService: AlunoService,
    private activatedRoute: ActivatedRoute,
    private router: Router) {

    this.form = this.fb.nonNullable.group({
      id: [null as number | null],
      nome: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      sobrenome: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(60)]],
      dataNascimento: [null as Date | null, [Validators.required]],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/)]],
      email: ['', [Validators.required, Validators.email], Validators.maxLength(100)],
      telefones: this.fb.array([])
    });

  }

  ngOnInit(): void {
  }

  get telefones(): FormArray<FormGroup> {
    return this.form.get('telefones') as FormArray<FormGroup>;
  }

  private buildTelefoneGroup(codigoArea = '', numero = ''): FormGroup {
    return this.fb.group({
      codigoArea: [codigoArea, [Validators.required, Validators.minLength(2),
      Validators.maxLength(2), Validators.pattern(/^\d{2}$/)]],
      numero: [numero, [Validators.required, Validators.minLength(8),
      Validators.maxLength(9), Validators.pattern(/^\d{8,9}$/)]],
    })
  }

  adicionarTelefone(): void {
    this.telefones.push(this.buildTelefoneGroup());
  }

  removerTelefone(i: number): void {
    this.telefones.removeAt(i);
  }

  private onlyDigits(s: string | null | undefined): string {
    return (s ?? '').replace(/\D/g, '');
  }

  private toLocalDateString(value: Date | string | null | undefined): string | null {
    if (!value) return null;
    const d = value instanceof Date ? value : new Date(value);
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, '0');
    const dd = String(d.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`; // LocalDate no backend
  }

  salvar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const aluno = this.form.value;

    const dto = {
      id: aluno?.id,
      nome: (aluno.nome ?? '').trim,
      sobrenome: (aluno.sobrenome ?? '').trim,
      dataNascimento: this.toLocalDateString(aluno.dataNascimento),
      cpf: this.onlyDigits(aluno.cpf),
      email: (aluno.email ?? '').trim,
      telefones: (aluno.telefones ?? []).map((t:any) => ({
        codigoArea: this.onlyDigits(t?.codigoArea),
        numero: this.onlyDigits(t?.numero)
      }))
    }

    let resultado$ = (aluno.id) 
      ? this.alunoService.alterar(aluno) 
      : this.alunoService.incluir(aluno);

    resultado$.subscribe({
      next: (obj) => {
        this.router.navigateByUrl('/alunos');
        this.exibirMensagem('Aluno salvo com sucesso!');
      },
      error: (erro) => {
         this.exibirMensagem('Problema ao salvar o aluno, entre em contato com o suporte!');
      }
    })

  }

  excluir() {
    if (this.form.valid) {
      const aluno = this.form.value;
      if (aluno.id!=null){
        this.alunoService.excluir(aluno.id).subscribe({
          next: () => {
            this.router.navigateByUrl('/alunos');
            this.exibirMensagem('Aluno excluído com sucesso!');
          },
          error: (erro) => {
            this.exibirMensagem('Problema ao excluir o aluno, entre em contato com o suporte!');
          }
        })
      }
    }
  }

  exibirMensagem(mensagem: string): void {
    this.snack.open(mensagem, 'OK', { 
      duration: 2500,
      horizontalPosition: 'center',
      verticalPosition: 'top' 
    });
  }



}
