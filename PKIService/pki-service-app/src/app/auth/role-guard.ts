import { Injectable, inject } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivateFn } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';


@Injectable({
  providedIn: 'root'
})
class RoleGuardService {

  constructor(public authService: AuthenticationService, public router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

    const expectedRole = route.data['expectedRole'];
    const tokenRole : string | null = this.authService.getRole();
    if (this.authService.isLoggedIn() && tokenRole){
      if(expectedRole !== tokenRole) {
        this.router.navigate(['']);
        return false;
        }
        return true;
    }
    this.router.navigate(['']);
    return false;
    }

   }

   export const RoleGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
    return inject(RoleGuardService).canActivate(next, state);
  }

  