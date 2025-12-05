import { Routes } from '@angular/router';
import { AdministradorFormComponent } from './components/administrador/administrador-form/administrador-form.component';
import { AdministradorListComponent } from './components/administrador/administrador-list/administrador-list.component';
import { AutorFormComponent } from './components/autor/autor-form/autor-form.component';
import { AutorMangaListComponent } from './components/autor/autor-list/autor-list.component';
import { CadastroComponent } from './components/cadastro/cadastro.component';
import { CarrinhoComponent } from './components/carrinho/carrinho.component';
import { CompraFinalizadaComponent } from './components/compra-finalizada/compra-finalizada.component';
import { ConfirmarCompraComponent } from './components/compra/compra.component';
import { EscritorFormComponent } from './components/escritor/escritor-form/escritor-form.component';
import { EscritorNovelListComponent } from './components/escritor/escritor-list/escritor-list.component';
import { LoginComponent } from './components/login/login.component';
import { MangaCardListComponent } from './components/manga/manga-card-list/manga-card-list.component';
import { MangaFormComponent } from './components/manga/manga-form/manga-form.component';
import { MangaInfoComponent } from './components/manga/manga-info/manga-info.component';
import { MangaListComponent } from './components/manga/manga-list/manga-list.component';
import { NovelFormComponent } from './components/novel/novel-form/novel-form.component';
import { NovelInfoComponent } from './components/novel/novel-info/novel-info.component';
import { NovelListComponent } from './components/novel/novel-list/novel-list.component';
import { AcessoProibidoComponent } from './components/status/acessoproibido/acessoproibido.component';
import { DesconhecidoComponent } from './components/status/desconhecido/desconhecido.component';
import { AdminTemplateComponent } from './components/template/admin-template/admin-template.component';
import { UserTemplateComponent } from './components/template/user-template/user-template.component';
import { PerfilComponent } from './components/usuario/perfil/perfil.component';
import { UsuarioFormComponent } from './components/usuario/usuario-form/usuario-form.component';
import { UsuarioListComponent } from './components/usuario/usuario-list/usuario-list.component';
import { authGuard } from './guard/auth.guard';
import { NovelCardListComponent } from './components/novel/novel-card-list/novel-card-list.component';
import { PedidoListComponent } from './components/pedido/pedido-list/pedido-list.component';
import { PedidosMeusComponent } from './components/pedido/pedido-meus/pedido-list.component';
import { PagarPixComponent } from './components/pedido/pagar-pix/pagar-pix.component';
import { PagarCartaoComponent } from './components/pedido/pagar-cartao/pagar-cartao.component';

export const routes: Routes = [
    {
        path: '',
        title: 'Loja',
        component: UserTemplateComponent,
        children: [
            { path: '',pathMatch: 'full',redirectTo: 'loja' },
            { path: 'loja',component: MangaCardListComponent,title: 'Listagem de produtos' },
            { path: 'login',component: LoginComponent,title: 'Login' },
            { path: 'loja/manga/:id',component: MangaInfoComponent,title: 'Mangá' },
            { path: 'loja/novel/:id',component: NovelInfoComponent,title: 'Novel' },
            { path: 'cadastro',component: CadastroComponent,title: 'Cadastro' },
            { path: 'perfil',component: PerfilComponent,title: 'Perfil',canActivate: [authGuard] },
            { path: 'compras', component: ConfirmarCompraComponent, title: 'Confirma Compra', canActivate: [authGuard],data: {role: "user"}},
            { path: 'meuspedidos',component: PedidosMeusComponent, title: 'Meus Pedidos', canActivate:[authGuard],data: {role: "user"}},
            { path: 'meuspedidos/pagarpix/:id', component: PagarPixComponent, title: 'Pagar com Pix', canActivate: [authGuard],data: {role: "user"}},
            { path: 'meuspedidos/pagarcredito/:id', component: PagarCartaoComponent, title: 'Pagar com Cartão de Crédito', canActivate: [authGuard],data: {role: "user"}},
            { path: 'meuspedidos/pagardebito/:id', component: PagarCartaoComponent, title: 'Pagar com Cartão de Débito', canActivate: [authGuard],data: {role: "user"}},
        ]
    },
    {
        path: 'login',
        title: 'Login',
        component: LoginComponent,
    },
    {
        path: 'comprasfinalizadas',
        component: CompraFinalizadaComponent,
        title: 'Compras finalizadas',
        canActivate: [authGuard],data: { role: "user" },
    },
    {
        path: 'search/manga',
        title: 'Barra de pesquisa de mangás',
        component: UserTemplateComponent,
    },
    {
        path:'novels',
        title: 'Novels',
        component: NovelCardListComponent,
    },
    {
        path: 'search/manga/:term',
        title: 'Barra de pesquisa de mangás',
        component: UserTemplateComponent,
    },
    {
        path: 'search/novel',
        title: 'Barra de pesquisa de novels',
        component: UserTemplateComponent,
    },
    {
        path: 'search/novel/:term',
        title: 'Barra de pesquisa de novels',
        component: UserTemplateComponent,
    },
    {
        path: 'acesso-proibido',
        title: 'Acesso proibido',
        component: AcessoProibidoComponent,
    },
    {
        path: 'admin',
        title: 'Painel de controle',
        component: AdminTemplateComponent,
        canActivate: [authGuard],data: { role: "admin" },
        children: [
            { path: '',pathMatch: 'full',redirectTo: 'administrador' },
            { path: 'admin/loja',component: MangaCardListComponent,title: 'Listagem' },
            { path: 'pedido', component: PedidoListComponent, title: 'Listagem de Pedidos'},
            { path: 'administrador',component: AdministradorListComponent,data: { title: "AdministradorListComponent" } },
            { path: 'administrador/new',component: AdministradorFormComponent,data: { title: "AdministradorFormComponent" } },
            { path: 'administrador/edit/:id',component: AdministradorFormComponent,data: { title: "AdministradorFormComponent" } },
            { path: 'autor',component: AutorMangaListComponent,data: { title: "AutorListComponent" } },
            { path: 'autor/new',component: AutorFormComponent,data: { title: "AutorFormComponent" } },
            { path: 'autor/edit/:id',component: AutorFormComponent,data: { title: "AutorFormComponent" } },
            { path: 'escritor',component: EscritorNovelListComponent,data: { title: "EscritorListComponent" } },
            { path: 'escritor/new',component: EscritorFormComponent,data: { title: "EscritorFormComponent" } },
            { path: 'escritor/edit/:id',component: EscritorFormComponent,data: { title: "EscritorFormComponent" } },
            { path: 'manga',component: MangaListComponent,data: { title: "MangaListComponent" } },
            { path: 'manga/new',component: MangaFormComponent,data: { title: "MangaFormComponent" } },
            { path: 'manga/edit/:id',component: MangaFormComponent,data: { title: "MangaFormComponent" } },
            { path: 'novel',component: NovelListComponent,data: { title: "NovelListComponent" } },
            { path: 'novel/new',component: NovelFormComponent,data: { title: "NovelFormComponent" } },
            { path: 'novel/edit/:id',component: NovelFormComponent,data: { title: "NovelFormComponent" } },
            { path: 'usuario',component: UsuarioListComponent,data: { title: "UsuarioListComponent" } },
            { path: 'usuario/new',component: UsuarioFormComponent,data: { title: "UsuarioFormComponent" } },
            { path: 'usuario/edit/:id',component: UsuarioFormComponent,data: { title: "UsuarioFormComponent" } }
        ]
    },
    {
        path: '**',
        pathMatch: 'full',
        title: 'Página não encontrada',
        component: DesconhecidoComponent,
    },
]