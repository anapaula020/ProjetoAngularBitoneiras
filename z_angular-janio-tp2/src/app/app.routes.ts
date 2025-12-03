import { Routes } from '@angular/router';
import { AlunoFormComponent } from './components/aluno/aluno-form/aluno-form.component';
import { AlunoListComponent } from './components/aluno/aluno-list/aluno-list.component';
import { CarrinhoComponent } from './components/carrinho/carrinho.component';
import { CidadeFormComponent } from './components/cidade/cidade-form/cidade-form.component';
import { CidadeListComponent } from './components/cidade/cidade-list/cidade-list.component';
import { EstadoFormComponent } from './components/estado/estado-form/estado-form.component';
import { EstadoListComponent } from './components/estado/estado-list/estado-list.component';
import { PlanoCardListComponent } from './components/plano/plano-card-list/plano-card-list.component';
import { AdminTemplateComponent } from './components/template/admin-template/admin-template.component';
import { UserTemplateComponent } from './components/template/user-template/user-template.component';
import { alunoResolver } from './resolvers/aluno-resolver';
import { estadoResolver } from './resolvers/estado-resolver';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './guard/auth.guard';

export const routes: Routes = [

    {
        path: '',
        component: UserTemplateComponent,
        title: 'e-Commerce',
        canActivate: [authGuard],
        children: [
            {path: '', pathMatch: 'full', redirectTo: 'planos'},

            {path: 'planos', component: PlanoCardListComponent,  title: 'Card de Planos', data: {public: true}},
            {path: 'carrinho', component: CarrinhoComponent, title: 'Carrinho', data: {public: true}},
            {path: 'login', component: LoginComponent, title: 'Login', data: {public: true}},
        ]
    }, 

    {
        path: 'admin',
        component: AdminTemplateComponent,
        title: 'Area Administrativa',
        canActivate: [authGuard],
        children: [
            { path: '', pathMatch: 'full', redirectTo: 'estados'},

            { path: 'estados', component: EstadoListComponent, title: 'Lista de Estados' },
            { path: 'estados/new', component: EstadoFormComponent, title: 'Cadastro de Estados' },
            { path: 'estados/edit/:id', component: EstadoFormComponent, title: 'Edição de Estado', 
                resolve: {estado: estadoResolver}
            },

            { path: 'alunos', component: AlunoListComponent, title: 'Lista de Alunos' },
            { path: 'alunos/new', component: AlunoFormComponent, title: 'Cadastro de Alunos' },
            { path: 'alunos/edit/:id', component: AlunoFormComponent, title: 'Edição de Aluno', 
                resolve: {aluno: alunoResolver}
            },

            { path: 'cidades', component: CidadeListComponent, title: 'Lista de Cidades' },
            { path: 'cidades/new', component: CidadeFormComponent, title: 'Cadastro de Cidades' }
        ]
    },
];
