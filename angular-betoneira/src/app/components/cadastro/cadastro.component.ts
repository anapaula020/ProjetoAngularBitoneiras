import { CommonModule } from '@angular/common';
import { Component,OnInit } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,ValidationErrors,Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { FooterLoginComponent } from '../template/footer-login/footer-login.component';
import { HeaderLoginComponent } from '../template/header-login/header-login.component';
import { ClienteService } from '../../services/cliente.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'app-register',
    templateUrl: './cadastro.component.html',
    styleUrls: ['./cadastro.component.css'],
    imports: [CommonModule, MatFormFieldModule, ReactiveFormsModule, MatInputModule, MatButtonModule, MatCardModule, MatSelectModule, HeaderLoginComponent, FooterLoginComponent]
})
export class CadastroComponent implements OnInit {
    formGroup: FormGroup;
    constructor(
        private clienteService: ClienteService,private router: Router,private formBuilder: FormBuilder,private snackBar: MatSnackBar) {
        this.formGroup = this.formBuilder.group({
            username: [null,[Validators.required,Validators.minLength(4),Validators.maxLength(80)]],
            email: [null,[Validators.required,Validators.email,Validators.minLength(6),Validators.maxLength(60)]],
            senha: [null,[Validators.required,Validators.minLength(6),Validators.maxLength(60)]],
            cpf: [null,[Validators.required,Validators.minLength(10),Validators.maxLength(12)]]
        });
    }

    ngOnInit(): void {
    }

    register() {
        if(this.formGroup.valid) {
            const cliente = this.formGroup.value;
            this.clienteService.insert(cliente).subscribe({
                next: () => {
                    this.router.navigateByUrl('/login');
                },
                error: (err: any) => {
                    this.showSnackbarTopPosition("Erro ao cadastrar usuário.");
                },
            });
        }
    }

    showSnackbarTopPosition(content: string) {
        this.snackBar.open(content,'fechar',{
            duration: 3000,
            verticalPosition: 'top',
            horizontalPosition: 'center',
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
                        formControl.setErrors({ apiError: validationError.message });
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
            minlength: 'Nome de usuário deve conter ao menos 4 caracteres.',
            maxlength: 'Nome de usuário deve conter no máximo 80 caracteres.',
            apiError: 'API_ERROR'
        },
        email: {
            required: 'Email é obrigatório.',
            minlength: 'Email de usuário deve conter ao menos 6 caracteres.',
            maxlength: 'Email de usuário deve conter no máximo 60 caracteres.',
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
        sexo: {
            required: 'O sexo é obrigatório.',
            apiError: 'API_ERROR'
        }
    }
}
