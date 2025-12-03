import { CommonModule,NgIf } from '@angular/common';
import { ChangeDetectionStrategy,Component,OnInit,inject } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,ValidationErrors,Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { AdministradorService } from '../../../services/administrador.service';
import { FooterComponent } from '../../template/footer/footer.component';
import { HeaderComponent } from '../../template/header/header.component';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialog,MatDialogModule } from '@angular/material/dialog';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';

@Component({
    selector: 'app-administrador-form',
    standalone: true,
    templateUrl: './administrador-form.component.html',
    styleUrls: ['./administrador-form.component.css'],
    imports: [NgIf,ReactiveFormsModule,MatFormFieldModule,MatInputModule,MatButtonModule,MatCardModule,MatToolbarModule,RouterModule,MatSelectModule,CommonModule,HeaderComponent,FooterComponent,MatDialogModule],
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdministradorFormComponent implements OnInit {
    formGroup: FormGroup;
    adminId: number | null = null;
    readonly dialog = inject(MatDialog);

    constructor(private formBuilder: FormBuilder,private administradorService: AdministradorService,private router: Router,private activatedRoute: ActivatedRoute) {
        this.formGroup = this.formBuilder.group({
            id: [null],
            username: [null,[Validators.required,Validators.minLength(4),Validators.maxLength(80)]],
            email: [null,[Validators.required,Validators.minLength(6),Validators.maxLength(60)]],
            senha: [null,[Validators.required,Validators.minLength(6),Validators.maxLength(60)]],
            cpf: [null,[Validators.required,Validators.minLength(10),Validators.maxLength(12)]]
        });
    }

    ngOnInit(): void {
        this.activatedRoute.params.subscribe(params => {
            this.adminId = params['id'] ? +params['id'] : null;
            if(this.adminId) {
                this.loadAdministrador(this.adminId);
            }
        });

    }

    initializeForm(): void {
        const admin = this.activatedRoute.snapshot.data['admin'];
        if(admin) {
            this.formGroup.patchValue(admin);
        }
    }

    loadAdministrador(id: number): void {
        this.administradorService.findById(id).subscribe(admin => {
            let data = { ...admin };
            data.senha = "";
            this.formGroup.patchValue(data);
        });
        this.formGroup.markAsUntouched();
    }

    salvar(): void {
        const administrador = this.formGroup.value;
        if(administrador.id) {
            this.administradorService.update(administrador).subscribe(() => {
                this.router.navigateByUrl('/admin/administrador');
            },error => {
                this.tratarErros(error);
            });
        } else {
            this.administradorService.insert(administrador).subscribe(() => {
                this.router.navigateByUrl('/admin/administrador');
            },error => {
                this.tratarErros(error);
            });
        }
    }

    excluir(): void {
        const id = this.formGroup.get('id')?.value;
        if(id) {
            const dialogRef = this.dialog.open(ExclusaoComponent);
            dialogRef.afterClosed().subscribe(result => {
                if(result === true) {
                    this.administradorService.delete(id).subscribe(() => {
                        this.router.navigateByUrl('/admin/administrador');
                    },error => {
                        this.tratarErros(error);
                    });
                }
            });
        }
    }

    preencher(): void {
        this.formGroup.patchValue({
            username: "adminadmin",
            email: "admin@admin.com",
            senha: "123456789123456789",
            cpf: "1234567890",
        });
    };

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
        username: {
            required: 'Nome de usuário é obrigatório.',
            minlength: 'Nome deve conter ao menos 4 caracteres.',
            maxlength: 'Nome deve conter no máximo 80 caracteres.',
            apiError: 'API_ERROR'
        },
        email: {
            required: 'Email é obrigatório.',
            minlength: 'Email deve conter ao menos 6 caracteres.',
            maxlength: 'Email deve conter no máximo 60 caracteres.',
            apiError: 'API_ERROR'
        },
        senha: {
            required: 'Senha é obrigatória.',
            minlength: 'Senha deve conter ao menos 6 caracteres.',
            maxlength: 'Senha deve conter no máximo 60 caracteres.',
            apiError: 'API_ERROR'
        },
        cpf: {
            required: 'CPF é obrigatório.',
            minlength: 'CPF deve conter ao menos 10 caracteres.',
            maxlength: 'CPF deve conter no máximo 12 caracteres.',
            apiError: 'API_ERROR'
        },
    }
}