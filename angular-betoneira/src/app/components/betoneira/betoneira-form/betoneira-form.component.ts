import { CommonModule,NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component,OnInit,inject } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,ValidationErrors,Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldControl,MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { AutorService } from '../../../services/autorBetoneira.service';
import { BetoneiraService } from '../../../services/betoneira.service';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { AutorBetoneira } from '../../../models/autorBetoneira.model';
import { Betoneira } from '../../../models/betoneira.model';
import { MatIcon } from '@angular/material/icon';
import { GeneroBetoneira } from '../../../models/generoBetoneira.model';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-betoneira-form',
    standalone: true,
    templateUrl: './betoneira-form.component.html',
    styleUrls: ['./betoneira-form.component.css'],
    imports: [CommonModule,FooterAdminComponent,HeaderAdminComponent,MatIcon,MatButtonModule,MatCardModule,MatFormFieldModule,MatInputModule,MatSelectModule,MatToolbarModule,NgIf,ReactiveFormsModule,RouterModule]
})
export class BetoneiraFormComponent implements OnInit {
    formGroup: FormGroup;
    autores: AutorBetoneira[] = [];
    generos: GeneroBetoneira[] = [];
    betoneiraId: number | null = null;
    dialog = inject(MatDialog);

    fileName: string = '';
    selectedFile: File | null = null;
    imagePreview: string | ArrayBuffer | null = null;

    constructor(private formBuilder: FormBuilder,private betoneiraService: BetoneiraService,private autorService: AutorService,private router: Router,private activatedRoute: ActivatedRoute) {
        this.formGroup = this.formBuilder.group({
            id: [null],
            nome: [null,[Validators.required,Validators.minLength(3),Validators.maxLength(80)]],
            sinopse: ['',[Validators.required,Validators.minLength(10),Validators.maxLength(1000)]],
            genero: [null],
            idAutor: [null],
            lancamento: [null,[Validators.required,Validators.min(0)]],
            color: [null,[Validators.required,Validators.minLength(2),Validators.maxLength(12)]],
            preco: [null,[Validators.required,Validators.min(0)]],
            estoque: [null,[Validators.required,Validators.min(0)]],
            paginas: [null,[Validators.required,Validators.min(0)]],
            imageUrl: [null]
        });
    }

    ngOnInit(): void {
        const betoneira: Betoneira = this.activatedRoute.snapshot.data['betoneira'];
        this.autorService.findAll().subscribe((data) => {
            this.autores = data;
        });
        this.betoneiraService.findGeneros().subscribe((data) => {
            this.generos = data;
        });
        this.activatedRoute.params.subscribe(params => {
            this.betoneiraId = params['id'] ? +params['id'] : null;
            if(this.betoneiraId) {
                this.loadBetoneira(this.betoneiraId);

            }

        });
        this.initializeForm();

    }

    initializeForm(): void {
        const betoneira = this.activatedRoute.snapshot.data['betoneira'];
        const genero = this.generos.find(m => m.id === (betoneira?.genero?.id || null));
        if(betoneira && betoneira.imageUrl) {
            this.imagePreview = this.betoneiraService.toImageUrl(betoneira.imageUrl);
            this.fileName = betoneira.imageUrl;
        }

        this.formGroup = this.formBuilder.group({
            id: [(betoneira && betoneira.id) ? betoneira.id : null],
            nome: [(betoneira && betoneira.nome) ? betoneira.nome : null],
            sinopse: [(betoneira && betoneira.sinopse) ? betoneira.sinopse : null],
            genero: [genero],
            idAutor: [(betoneira && betoneira.idAutor) ? betoneira.idAutor : null],
            lancamento: [(betoneira && betoneira.lancamento) ? betoneira.lancamento : null],
            color: [(betoneira && betoneira.color) ? betoneira.color : null],
            preco: [(betoneira && betoneira.preco) ? betoneira.preco : null],
            estoque: [(betoneira && betoneira.estoque) ? betoneira.estoque : null],
            paginas: [(betoneira && betoneira.paginas) ? betoneira.paginas : null],
            imageUrl: [(betoneira && betoneira.imageUrl) ? betoneira.imageUrl : null]
        });
    }

    private uploadImage(betoneiraId: number) {

        if(this.selectedFile) {
            this.betoneiraService.uploadImage(betoneiraId,this.selectedFile.name,this.selectedFile)

                .subscribe({
                    next: () => {
                        this.voltarPagina();
                    },
                    error: err => {
                    }
                })
        }
        else {
            this.voltarPagina();
        }
    }

    voltarPagina(): void {
        this.router.navigateByUrl('/admin/betoneira');
    }

    loadBetoneira(id: number): void {
        if(id != null && id > 0) {
            this.betoneiraService.findById(id).subscribe(betoneira => {
                this.formGroup.patchValue(betoneira);
                if(betoneira.imageUrl) {
                    this.imagePreview = this.betoneiraService.toImageUrl(betoneira.imageUrl);
                    this.fileName = betoneira.imageUrl;
                }
                if(betoneira.idAutor) {
                    this.formGroup.get('idAutor')?.setValue(betoneira.idAutor.id);
                }
                if(betoneira.genero) {
                    const generoSelecionado = this.generos.find(m => m.id === (betoneira?.genero?.id || null));
                    this.formGroup.get('genero')?.setValue(generoSelecionado);
                }

            });
            this.formGroup.markAllAsTouched();
        }
    }

    carregarImagemSelecionada(event: any) {
        this.selectedFile = event.target.files[0];

        if(this.selectedFile) {
            this.fileName = this.selectedFile.name;

            const reader = new FileReader();
            reader.onload = e => this.imagePreview = reader.result;
            reader.readAsDataURL(this.selectedFile);
        }
    }

    salvar() {
        this.formGroup.markAllAsTouched();
        if(this.formGroup.valid) {
            const betoneira = this.formGroup.value;

            if(!this.selectedFile && this.imagePreview) {
                betoneira.imageUrl = this.fileName;
            }
            const operacao = betoneira.id == null
                ? this.betoneiraService.insert(betoneira)
                : this.betoneiraService.update(betoneira);

            operacao.subscribe({
                next: (betoneiraCadastrada) => {
                    this.betoneiraService.findAll();
                    if(this.betoneiraId !== null) {
                        this.uploadImage(this.betoneiraId);
                    }

                    this.uploadImage(betoneiraCadastrada.id);
                },
                error: (error) => {
                    this.tratarErros(error);
                }
            });
        }
    }


    excluir(): void {
        const dialogRef = this.dialog.open(ExclusaoComponent);
        dialogRef.afterClosed().subscribe(result => {
            if(result === true) {
                const id = this.formGroup.get('id')?.value;
                if(id) {
                    this.betoneiraService.delete(id).subscribe(() => {
                        this.router.navigateByUrl('/admin/betoneira');
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
            minlength: 'Sinopse deve conter ao menos 10 caracteres.',
            maxlength: 'Sinopse deve conter no máximo 1000 caracteres.',
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
        color: {
            required: 'Color é obrigatório.',
            minlength: 'Color deve conter ao menos 1 caracteres.',
            maxlength: 'Color deve conter no máximo 12 caracteres.',
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
        }
    }
}