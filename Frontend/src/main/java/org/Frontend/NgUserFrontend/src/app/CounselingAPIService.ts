import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { CanActivate } from "@angular/router";
import { ActivatedRouteSnapshot } from "@angular/router";
import { RouterStateSnapshot } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Promise } from "q";




@Injectable()
export class CounselingAPIService {


    constructor( private http: HttpClient ) { }

    //constructor( private cookieService:CookieService) {};


    loginCheck(){//: string {
        
      

        return this.http
            .get<string>( 'http://localhost:54321/rest/service/logincheck', { withCredentials: true } );
        /*    .subscribe(
            ( response: string ) => {

              //  console.log( 'resp: ' + response );
               // this.returnValLC = response;

            }, ( err ) => {
                console.log( 'Error: ' + err );
               // this.returnValLC ="error occured in LC"
            } );
*/
      //  return this.returnValLC;
    }

    loginService( inId: string, inPw: string, isChecked: boolean ){//: string {
        
        let body = `id=${inId}&pw=${inPw}&empswitch=${isChecked}`;

        //response a CustomSuccessHandlerbol
        
        return this.http
            .post<string>( 'http://localhost:54321/dashboard/login', body, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, responseType: 'json', withCredentials: true } )
        /*    .subscribe(
            ( response: string ) => {
                
                this.returnValL = response;                
            }, ( err ) => {
                console.log( 'Error: ' + err );
                this.returnValL = "error occured in L";
            } 
        
 
        return this.returnValL;
*/
    }


    bookingsService(){
        return this.http
        .post<string>( 'http://localhost:54321/rest/service/all/bookings', { headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, responseType: 'json', withCredentials: true } )

    }

    logout(){
        return this.http
            .get<string>( 'http://localhost:54321/logout', { withCredentials: true } );
    }


}
