import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthenticationService} from "../../api/services/authentication.service";

@Injectable({providedIn: "root"})
export class AuthenticationGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService,
              private router: Router) {}
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if(!this.isAuthenticated()) {
      this.navigateToLogin();
      return false;
    }

    let url = route.routeConfig?.path ?? '';

    if(url.indexOf('admin') > -1) {
      if(this.isAdmin()) {
        return true;
      }
      this.navigateToCustomer();
      return false;
    }

    if(!this.isCustomer()) {
      this.navigatoToAdmin();
      return false;
    }

    return true;
  }

  private isAuthenticated(): boolean {
    return localStorage.getItem('user') !== '' ;
  }

  private isAdmin(): boolean {
    let user = JSON.parse(localStorage.getItem('user')!);
    if(user.roles.some((role:string) => role === 'ADMIN')) {
      return true;
    }
    return false;
  }

  private isCustomer(): boolean {
    let user = JSON.parse(localStorage.getItem('user')!);
    if(user.roles.some((role:string) => role === 'CUSTOMER')) {
      return true;
    }
    return false;
  }

  private navigateToLogin(): void {
    this.router.navigate(['/login']);
  }

  private navigateToCustomer(): void {
    this.router.navigate(['/employee']);
  }

  private navigatoToAdmin(): void {
    this.router.navigate(['/admin/users']);
  }
}
