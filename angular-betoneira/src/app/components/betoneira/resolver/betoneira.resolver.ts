import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Betoneira } from "../../../models/betoneira.model";
import { BetoneiraService } from "../../../services/betoneira.service";
import { inject } from "@angular/core";

export const betoneiraResolver: ResolveFn<Betoneira> =
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(BetoneiraService).findById(parseInt(route.paramMap.get('id')!, 10));
    }