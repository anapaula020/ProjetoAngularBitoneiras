import { Component } from '@angular/core';
import { FormBuilder,FormGroup,ReactiveFormsModule,Validators } from '@angular/forms';
import { MunicipioService } from '../../../services/municipio.service';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { NgIf } from '@angular/common';
import { MatInputModule } from '@angular/material/input';


@Component({
    selector: 'app-municipio-form',
    standalone: true,
    imports: [ReactiveFormsModule, MatFormFieldModule, MatButtonModule, NgIf, MatInputModule],
    templateUrl: './municipio-form.component.html',
    styleUrl: './municipio-form.component.css'
})

export class MunicipioFormComponent {
    formGroup: FormGroup;

    constructor(private formBuilder: FormBuilder, private municipioService: MunicipioService, private router: Router) {
        this.formGroup = this.formBuilder.group({
            nome: ['', Validators.required], 
            cidade: ['', Validators.required]
        })
    }

    onSubmit() {
        if(this.formGroup.valid) {
            const novoMunicipio = this.formGroup.value;
            this.municipioService.insert(novoMunicipio).subscribe({
                next: (municipioCadastrado) => {
                    this.router.navigateByUrl('/municipios');
                },
                error: (err) => {
                    console.log('Erro ao salvar', JSON.stringify(err));
                }
            })
        }
    }
}
/* import { Component } from '@angular/core';

import { Hero } from '../../../models/hero.model';

@Component({
    selector: 'app-hero-form',
    templateUrl: './municipio-form.component.html',
    styleUrls: ['./municipio-form.component.css']
})
export class MunicipioFormComponent {

    powers = ['Really Smart','Super Flexible',
        'Super Hot','Weather Changer'];

    model = new Hero(18,'Dr. IQ',this.powers[0],'Chuck Overstreet');

    submitted = false;

    onSubmit() { this.submitted = true; }

    newHero() {
        this.model = new Hero(42,'','');
    }
} */