import { Routes } from '@angular/router';
import { MunicipioListComponent } from './components/municipio/municipio-list/municipio-list.component';
import { MunicipioFormComponent } from './components/municipio/municipio-form/municipio-form.component';

export const routes: Routes = [
    {path: 'municipios', component: MunicipioListComponent, title: 'Lista de Municipios'},
    {path: 'municipios/new', component: MunicipioFormComponent, title: 'Novo Municipio'}
];
