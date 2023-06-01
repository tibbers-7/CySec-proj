import { Injectable, inject } from "@angular/core";
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivateFn } from "@angular/router";
import { AuthenticationService } from "../services/authentication.service";

@Injectable({
    providedIn: 'root'
  })
  class AuthGuardService {
  
    constructor(public authService: AuthenticationService, public router: Router) { }
  
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
  
      if (this.authService.isLoggedIn()) return true
      this.router.navigate([''])
      return false;
      }
  
     }
  
     export const AuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
      return inject(AuthGuardService).canActivate(next, state)
     }