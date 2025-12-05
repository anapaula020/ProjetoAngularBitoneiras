import { CommonModule,NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component,OnInit, inject } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,ValidationErrors,Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { GeneroNovelMap } from '../../../models/generoNovel.model';
import { EscritorNovelService } from '../../../services/escritor.service';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-escritor-form',
    standalone: true,
    templateUrl: './escritor-form.component.html',
    styleUrls: ['./escritor-form.component.css'],
    imports: [NgIf,ReactiveFormsModule,MatFormFieldModule,MatInputModule,MatButtonModule,MatCardModule,MatToolbarModule,RouterModule,MatSelectModule,CommonModule,HeaderAdminComponent,FooterAdminComponent]
})
export class EscritorFormComponent implements OnInit {
    formGroup: FormGroup;
    generos = Object.entries(GeneroNovelMap);
    escritorId: number | null = null;
    readonly dialog = inject(MatDialog);

    constructor(
        private formBuilder: FormBuilder,
        private escritorService: EscritorNovelService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        this.formGroup = this.formBuilder.group({
            id: [null],
            nome: [null,[Validators.required,Validators.minLength(3),Validators.maxLength(40)]],
            anoNascimento: [null,[Validators.required,Validators.min(0),Validators.max(9999)]],
            nacionalidade: [null,[Validators.required,Validators.minLength(2),Validators.maxLength(30)]],
            sexo: [null,[Validators.required]]
        });
    }

    ngOnInit(): void {
        this.activatedRoute.params.subscribe(params => {
            this.escritorId = params['id'] ? +params['id'] : null;
            if(this.escritorId) {
                this.loadEscritor(this.escritorId);
            }
        });

    }

    initializeForm(): void {
        if(this.activatedRoute.snapshot.data['escritor']) {
            this.formGroup.patchValue(this.activatedRoute.snapshot.data['escritor']);
        }
    }

    loadEscritor(id: number): void {
        this.escritorService.findById(id).subscribe(escritor => {
            this.formGroup.patchValue(escritor);
        });
        this.formGroup.markAllAsTouched();
    }

    salvar(): void {
        if(this.formGroup.invalid) {
            this.formGroup.markAllAsTouched();
            return;
        }
        if(this.formGroup.valid) {
            const escritor = this.formGroup.value;
            if(escritor.id) {
                this.escritorService.update(escritor).subscribe(() => {
                    this.router.navigateByUrl('/admin/escritor');
                },error => {
                    this.tratarErros(error);
                });
            } else {
                this.escritorService.insert(escritor).subscribe(() => {
                    this.router.navigateByUrl('/admin/escritor');
                },error => {
                    this.tratarErros(error);
                });
            }
        }
    }

    excluir(): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                const id = this.formGroup.get('id')?.value;
                if(id) {
                    this.escritorService.delete(id).subscribe(() => {
                        this.router.navigateByUrl('/admin/escritor');
                    },error => {
                        this.tratarErros(error);
                    });
                }
            };
        });
    }

    getErrorMessage(controlName: string,errors: ValidationErrors | null | undefined): string {
        if(!errors) return "";
        for(const errorName in errors) {
            if(errors.hasOwnProperty(errorName) && this.errorMessages[controlName][errorName]) {
                return this.errorMessages[controlName][errorName];
            }
        }
        return "Parâmetro inválido.";
    }

    tratarErros(errorResponse: HttpErrorResponse) {
        if(errorResponse.status === 400) {
            if(errorResponse.error?.errors) {
                errorResponse.error.errors.forEach((validationError: any) => {
                    const formControl = this.formGroup.get(validationError.fieldName);
                    if(formControl) {
                        formControl.setErrors({ apiError: validationError.message })
                    }
                });
            }
        } else if(errorResponse.status < 400) {
        } else if(errorResponse.status >= 500) {
        }
    }

    errorMessages: { [controlName: string]: { [errorName: string]: string } } = {
        nome: {
            required: 'Nome é obrigatório.',
            minlength: 'Nome deve conter ao menos 3 caracteres.',
            maxlength: 'Nome deve conter no máximo 40 caracteres.',
            apiError: 'API_ERROR'
        },
        anoNascimento: {
            required: 'Ano de nascimento é obrigatório.',
            min: 'Ano de nascimento deve ser maior do que 0.',
            max: 'Ano de nascimento deve ser menor do que 9999.',
            apiError: 'API_ERROR'
        },
        nacionalidade: {
            required: 'Nacionalidade é obrigatório.',
            minlength: 'Nacionalidade deve conter ao menos 2 caracteres.',
            maxlength: 'Nacionalidade deve conter no máximo 30 caracteres.',
            apiError: 'API_ERROR'
        },
        sexo: {
            required: 'Sexo é obrigatório.',
            apiError: 'API_ERROR'
        }
    }
}