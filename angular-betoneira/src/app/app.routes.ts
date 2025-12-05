import { Routes } from "@angular/router";
import { authGuard } from "./guard/auth.guard";

import { UserTemplateComponent } from "./components/template/user-template/user-template.component";
import { AdminTemplateComponent } from "./components/template/admin-template/admin-template.component";

import { LoginComponent } from "./components/login/login.component";
import { AcessoProibidoComponent } from "./components/status/acesso-proibido/acesso-proibido.component";
import { DesconhecidoComponent } from "./components/status/desconhecido/desconhecido.component";
import { BetoneiraCardListComponent } from "./components/betoneira/betoneira-card-list/betoneira-card-list.component";
import { BetoneiraFormComponent } from "./components/betoneira/betoneira-form/betoneira-form.component";
import { BetoneiraInfoComponent } from "./components/betoneira/betoneira-info/betoneira-info.component";
import { BetoneiraListComponent } from "./components/betoneira/betoneira-list/betoneira-list.component";    
import { CarrinhoComponent } from "./components/carrinho/carrinho.component";
import { CompraComponent } from "./components/compra/compra.component";
import { CompraFinalizadaComponent } from "./components/compra-finalizada/compra-finalizada.component";
import { EnderecoFormComponent } from "./components/endereco/endereco-form/endereco-form.component";
import { EnderecoListComponent } from "./components/endereco/endereco-list/endereco-list.component";
import { FabricanteFormComponent } from "./components/fabricante/fabricante-form/fabricante-form.component";
import { FabricanteListComponent } from "./components/fabricante/fabricante-list/fabricante-list.component";
import { ItemPedidoFormComponent } from "./components/itempedido/itempedido-form/itempedido-form.component";
import { ItemPedidoListComponent } from "./components/itempedido/itempedido-list/itempedido-list.component";
import { PagamentoFormComponent } from "./components/pagamento/pagamento-form/pagamento-form.component";
import { PagamentoListComponent } from "./components/pagamento/pagamento-list/pagamento-list.component";
import { PagarCartaoComponent } from "./components/pedido/pagar-cartao/pagar-cartao.component";
import { PagarPixComponent } from "./components/pedido/pagar-pix/pagar-pix.component";
import { PedidoListComponent } from "./components/pedido/pedido-list/pedido-list.component";
import { PedidosMeusComponent } from "./components/pedido/pedido-meus/pedido-list.component";

export const routes: Routes = [
    {
        path: "",
        component: UserTemplateComponent,
        children: [
            { path: "", redirectTo: "loja", pathMatch: "full" },
            { path: "loja", component: BetoneiraCardListComponent },
            { path: "betoneiras/:id", component: BetoneiraInfoComponent },
            { path: "betoneiras/search/nome/:nome", component: BetoneiraCardListComponent },
            { path: "carrinho", component: CarrinhoComponent },
            { path: "compra", component: CompraComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "compra-finalizada", component: CompraFinalizadaComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "enderecos", component: EnderecoListComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "enderecos/:id", component: EnderecoFormComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "enderecos/meus-enderecos", component: EnderecoListComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "pedidos", component: PedidoListComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "pedidos/meus-pedidos", component: PedidosMeusComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "pedidos/:id", component: PedidoListComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "pagamentos", component: PagamentoListComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "pagamentos/:id", component: PagamentoFormComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "pagamentos/search/:nome", component: PagamentoListComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "meuspedidos/pagarpix/:id", component: PagarPixComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "meuspedidos/pagarcredito/:id", component: PagarCartaoComponent, canActivate: [authGuard], data: { role: "user" } },
            { path: "meuspedidos/pagardebito/:id", component: PagarCartaoComponent, canActivate: [authGuard], data: { role: "user" } },
        ]
    },
    {
        path: "login",
        component: LoginComponent
    },
    {
        path: "admin",
        component: AdminTemplateComponent,
        canActivate: [authGuard],
        data: { role: "admin" },
        children: [
            { path: "", redirectTo: "betoneira", pathMatch: "full" },
            { path: "betoneira", component: BetoneiraListComponent },
            { path: "betoneira/new", component: BetoneiraFormComponent },
            { path: "betoneira/edit/:id", component: BetoneiraFormComponent },
            { path: "fabricante", component: FabricanteListComponent },
            { path: "fabricante/new", component: FabricanteFormComponent },
            { path: "fabricante/edit/:id", component: FabricanteFormComponent },
            { path: "endereco", component: EnderecoListComponent },
            { path: "endereco/new", component: EnderecoFormComponent },
            { path: "endereco/edit/:id", component: EnderecoFormComponent },
            { path: "item-pedido", component: ItemPedidoListComponent },
            { path: "item-pedido/new", component: ItemPedidoFormComponent },
            { path: "item-pedido/edit/:id", component: ItemPedidoFormComponent },
            { path: "pagamento", component: PagamentoListComponent },
            { path: "pagamento/new", component: PagamentoFormComponent },
            { path: "pagamento/edit/:id", component: PagamentoFormComponent },
            { path: "pedido", component: PedidoListComponent },
        ]
    },
    {
        path: "acesso-proibido",
        component: AcessoProibidoComponent
    },
    {
        path: "**",
        component: DesconhecidoComponent
    }
];
