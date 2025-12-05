import { CommonModule } from '@angular/common';
import { Component,OnInit,inject } from '@angular/core';
import { FormBuilder,FormGroup,FormsModule,ReactiveFormsModule,ValidationErrors,Validators } from '@angular/forms';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { FabricanteService } from '../../../services/fabricante.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { HttpErrorResponse } from '@angular/common/http';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-autor-form',
    standalone: true,
    templateUrl: './autor-form.component.html',
    styleUrls: ['./autor-form.component.css'],
    imports: [MatDatepickerModule,MatNativeDateModule,CommonModule,FormsModule,ReactiveFormsModule,MatFormFieldModule,MatInputModule,MatButtonModule,MatCardModule,MatToolbarModule,MatSelectModule,RouterModule,HeaderAdminComponent,FooterAdminComponent],
    providers: [MatDatepickerModule]
})
export class FabricanteFormComponent implements OnInit {
    formGroup: FormGroup;
    autorId: number | null = null;
    readonly dialog = inject(MatDialog);

    constructor(private formBuilder: FormBuilder,private autorService: FabricanteService,private router: Router,private activatedRoute: ActivatedRoute) {
        this.formGroup = this.formBuilder.group({
            id: [null],
            nome: [null,[Validators.required,Validators.minLength(3),Validators.maxLength(40)]]
        });
    }

    ngOnInit(): void {
        this.activatedRoute.params.subscribe(params => {
            this.autorId = params['id'] ? +params['id'] : null;
            if(this.autorId) {
                this.loadAutor(this.autorId);
            }
        });
    }

    loadAutor(id: number): void {
        this.autorService.findById(id).subscribe(autor => {
            this.formGroup.patchValue(autor);
        });
    }

    salvar(): void {
        if(this.formGroup.invalid) {
            this.formGroup.markAllAsTouched();
            return;
        }
        if(this.formGroup.valid) {
            if(this.autorId) {
                this.autorService.update(this.formGroup.value).subscribe(() => {
                    this.router.navigateByUrl('/admin/autor');
                },error => {
                    this.tratarErros(error);
                });
            } else {
                this.autorService.insert(this.formGroup.value).subscribe(() => {
                    this.router.navigateByUrl('/admin/autor');
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
                if(this.formGroup.get('id')?.value) {
                    this.autorService.delete(this.formGroup.get('id')?.value).subscribe(() => {
                        this.router.navigateByUrl('/admin/autor');
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
        return "parâmetro inválido";
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
        }
    }
}