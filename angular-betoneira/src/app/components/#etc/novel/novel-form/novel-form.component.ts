import { CommonModule,NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component,OnInit, inject } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,ValidationErrors,Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { GeneroNovelMap } from '../../../models/generoNovel.model';
import { EscritorNovelService } from '../../../services/escritor.service';
import { NovelService } from '../../../services/novel.service';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";

@Component({
    selector: 'app-novel-form',
    standalone: true,
    templateUrl: './novel-form.component.html',
    styleUrls: ['./novel-form.component.css'],
    imports: [NgIf,ReactiveFormsModule,MatFormFieldModule,MatInputModule,MatButtonModule,MatCardModule,MatToolbarModule,RouterModule,MatSelectModule,CommonModule,HeaderAdminComponent,FooterAdminComponent]
})
export class NovelFormComponent implements OnInit {
    formGroup: FormGroup;
    autores: any[] = [];
    generos = Object.entries(GeneroNovelMap);
    novelId: number | null = null;
    readonly dialog = inject(MatDialog);

    constructor(private formBuilder: FormBuilder,private novelService: NovelService,private escritorService: EscritorNovelService,private router: Router,private activatedRoute: ActivatedRoute) {
        this.formGroup = this.formBuilder.group({
            id: [null],
            nome: [null,[Validators.required,Validators.minLength(3),Validators.maxLength(40)]],
            sinopse: [null,[Validators.required,Validators.minLength(10),Validators.maxLength(1000)]],
            genero: [null,[Validators.required,Validators.min(0)]],
            idAutor: [null,[Validators.required,Validators.min(0)]],
            lancamento: [null,[Validators.required,Validators.min(0)]],
            preco: [null,[Validators.required,Validators.min(0)]],
            estoque: [null,[Validators.required,Validators.min(0)]],
            paginas: [null,[Validators.required,Validators.min(0)]],
            capitulos: [null,[Validators.required,Validators.min(0)]]
        });
    }

    ngOnInit(): void {
        this.escritorService.findAll().subscribe((data) => (this.autores = data));
        this.activatedRoute.params.subscribe(params => {
            this.novelId = params['id'] ? +params['id'] : null;
            if(this.novelId) {
                this.loadNovel(this.novelId);
            }
        });
    }

    initializeForm(): void {
        const novel = this.activatedRoute.snapshot.data['novel'];
        if(novel) {
            this.formGroup.patchValue(novel);
        }
    }

    loadNovel(id: number): void {
        this.novelService.findById(id).subscribe(novel => {
            this.formGroup.patchValue(novel);
            this.formGroup.patchValue({ idAutor: novel.idAutor.id });
        });
        this.formGroup.markAllAsTouched();
    }

    salvar(): void {
        if(this.formGroup.invalid) {
            this.formGroup.markAllAsTouched();
            return;
        }
        if(this.formGroup.valid) {
            const novel = this.formGroup.value;
            if(novel.id) {
                this.novelService.update(novel).subscribe(() => {
                    this.router.navigateByUrl('/admin/novel');
                },error => {
                    this.tratarErros(error);
                });
            } else {
                this.novelService.insert(novel).subscribe(() => {
                    this.router.navigateByUrl('/admin/novel');
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
                    this.novelService.delete(id).subscribe(() => {
                        this.router.navigateByUrl('/admin/novel');
                    },error => {
                        this.tratarErros(error);
                    });
                }
            }
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
        sinopse: {
            required: 'Sinopse é obrigatório.',
            minlength: 'Sinopse deve conter ao menos 30 caracteres.',
            maxlength: 'Sinopse deve conter ao menos 1000 caracteres.',
            apiError: 'API_ERROR'
        },
        genero: {
            required: 'Gênero é obrigatório.',
            min: 'Gênero deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        idAutor: {
            required: 'Id do autor é obrigatório.',
            min: 'Id do autor deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        lancamento: {
            required: 'Ano de lançamento é obrigatório.',
            min: 'Ano de lançamento deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        preco: {
            required: 'Preço é obrigatório.',
            min: 'Preço deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        estoque: {
            required: 'Estoque é obrigatório.',
            min: 'Estoque deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        paginas: {
            required: 'Páginas é obrigatório.',
            min: 'Páginas deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        capitulos: {
            required: 'Páginas é obrigatório.',
            min: 'Capitulos deve ser maior do que 0.',
            apiError: 'API_ERROR'
        }
    }
}