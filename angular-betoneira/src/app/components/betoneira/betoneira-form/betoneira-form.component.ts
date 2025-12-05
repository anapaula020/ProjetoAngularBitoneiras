import { CommonModule,NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component,OnInit,inject } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,ValidationErrors,Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute,Router,RouterModule } from '@angular/router';
import { FabricanteService } from '../../../services/fabricante.service';
import { BetoneiraService } from '../../../services/betoneira.service';
import { HeaderAdminComponent } from "../../template/header-admin/header-admin.component";
import { FooterAdminComponent } from "../../template/footer-admin/footer-admin.component";
import { Fabricante } from '../../../models/fabricante.model';
import { Betoneira } from '../../../models/betoneira.model';
import { MatIcon } from '@angular/material/icon';
import { ExclusaoComponent } from '../../confirmacao/exclusao/exclusao.component';
import { MatDialog } from '@angular/material/dialog';
import { TipoBetoneira } from '../../../models/tipoBetoneira.model';

@Component({
    selector: 'app-betoneira-form',
    standalone: true,
    templateUrl: './betoneira-form.component.html',
    styleUrls: ['./betoneira-form.component.css'],
    imports: [CommonModule,FooterAdminComponent,HeaderAdminComponent,MatIcon,MatButtonModule,MatCardModule,MatFormFieldModule,MatInputModule,MatSelectModule,MatToolbarModule,NgIf,ReactiveFormsModule,RouterModule]
})
export class BetoneiraFormComponent implements OnInit {
    formGroup: FormGroup;
    fabricantes: Fabricante[] = [];
    betoneiraId: number | null = null;
    dialog = inject(MatDialog);
    tipos: TipoBetoneira[] = [];

    fileName: string = '';
    selectedFile: File | null = null;
    imagePreview: string | ArrayBuffer | null = null;

    constructor(private formBuilder: FormBuilder,private betoneiraService: BetoneiraService,private fabricanteService: FabricanteService,private router: Router,private activatedRoute: ActivatedRoute) {
        this.formGroup = this.formBuilder.group({
            id: [null],
            nome: [null,[Validators.required,Validators.minLength(3),Validators.maxLength(80)]],
            descricao: ['',[Validators.required,Validators.minLength(10),Validators.maxLength(1000)]],
            preco: [null,[Validators.required,Validators.min(0)]],
            quantidadeEstoque: [null,[Validators.required]],
            modelo: [null,[Validators.required,Validators.minLength(3),Validators.maxLength(80)]],
            marca: ['',[Validators.required,Validators.minLength(10),Validators.maxLength(1000)]],
            tipoBetoneira: [null],
            fabricante: [null],
            imageUrl: [null]
        });
    }

    ngOnInit(): void {
        this.fabricanteService.findAll().subscribe((data) => {
            this.fabricantes = data;
        });
        const betoneira: Betoneira = this.activatedRoute.snapshot.data['betoneira'];
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
        if(betoneira && betoneira.imageUrl) {
            this.imagePreview = this.betoneiraService.toImageUrl(betoneira.imageUrl);
            this.fileName = betoneira.imageUrl;
        }

        this.formGroup = this.formBuilder.group({
            id: [(betoneira && betoneira.id) ? betoneira.id : null],
            nome: [(betoneira && betoneira.nome) ? betoneira.nome : null],
            descricao: [(betoneira && betoneira.descricao) ? betoneira.descricao : null],
            preco: [(betoneira && betoneira.preco) ? betoneira.preco : null],
            tipoBetoneira: [(betoneira && betoneira.tipoBetoneira) ? betoneira.tipoBetoneira : null],
            fabricante: [(betoneira && betoneira.fabricante) ? betoneira.fabricante : null],
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
                if(betoneira.idFabricante) {
                    this.formGroup.get('fabricante')?.setValue(betoneira.idFabricante.id);
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
                    const formControl = this.formGroup.get(validationError.ifeldName);
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
        descricao: {
            required: 'Descricao é obrigatório.',
            minlength: 'Descricao deve conter ao menos 10 caracteres.',
            maxlength: 'Descricao deve conter no máximo 1000 caracteres.',
            apiError: 'API_ERROR'
        },
        preco: {
            required: 'Preço é obrigatório.',
            min: 'Preço deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        quantidadeEstoque: {
            required: 'Quantidade de estoque é obrigatório.',
            min: 'Quantidade de estoque deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        modelo: {
            required: 'Modelo é obrigatório.',
            minlength: 'Modelo deve conter ao menos 3 caracteres.',
            maxlength: 'Modelo deve conter no máximo 40 caracteres.',
            apiError: 'API_ERROR'
        },
        marca: {
            required: 'Marca é obrigatório.',
            minlength: 'Marca deve conter ao menos 10 caracteres.',
            maxlength: 'Marca deve conter no máximo 1000 caracteres.',
            apiError: 'API_ERROR'
        },
        capacidade: {
            required: 'Capacidade é obrigatório.',
            min: 'Capacidade deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        tipoBetoneira: {
            required: 'Id da betoneira é obrigatório.',
            min: 'Id da betoneira deve ser maior do que 0.',
            apiError: 'API_ERROR'
        },
        idFabricante: {
            required: 'Id do fabricante é obrigatório.',
            min: 'Id do fabricante deve ser maior do que 0.',
            apiError: 'API_ERROR'
        }
    }
}