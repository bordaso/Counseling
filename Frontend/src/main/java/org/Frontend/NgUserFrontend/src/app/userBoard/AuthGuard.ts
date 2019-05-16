import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { CanActivate, CanActivateChild } from "@angular/router";
import { ActivatedRouteSnapshot } from "@angular/router";
import { RouterStateSnapshot } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Promise } from "q";
import { CounselingAPIService } from "src/app/CounselingAPIService";
import { map, filter, catchError, mergeMap } from 'rxjs/operators';




@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
    constructor(private http: HttpClient, private cas: CounselingAPIService) { }

    
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

        return this.cas.loginCheck().pipe(map(res => res==="LC1"));
    }

    canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.canActivate(route, state);
      }

    
   
    


  


    
}
