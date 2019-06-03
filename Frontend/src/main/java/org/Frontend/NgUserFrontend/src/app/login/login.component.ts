import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RequestOptions } from "@angular/http";
import { Input } from "@angular/core";
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { Output } from "@angular/core";
import { EventEmitter } from "@angular/core";
import { CounselingAPIService } from "src/app/CounselingAPIService";
import { Router } from "@angular/router";



@Component( {
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
} )
export class LoginComponent implements OnInit {

    @Input() inId = "";
    @Input() inPw = "";
    @Input() isChecked = false;

    incorrectCredentials = false;

    constructor( private http: HttpClient, private cas: CounselingAPIService, private router: Router ) { }

    ngOnInit() {
        this.cas.loginCheck().subscribe(
            ( response: string ) => {
                if ( response === "LC1" ) {
                    this.router.navigate( ['dashboard/user'] );
                } else if ( response === "LC2" ) {
                    window.location.href = "http://localhost:54321/dashboard/Dashboard.xhtml"
                }
            }, ( err ) => {
                console.log( 'Error: ' + err );
            } );
        //     let returnVal = this.loginCheck();

    }




    submit(){
        
        this.cas.loginService( this.inId, this.inPw, this.isChecked ).subscribe(
                ( response: string ) => {
                    
                    if ( response === "C1" ) {
                        this.incorrectCredentials = false;
                        this.router.navigate( ['dashboard/user'] );
                        return;
                    } else if ( response === "C2" ) {
                        this.incorrectCredentials = false;
                        window.location.href = "http://localhost:54321/dashboard/Dashboard.xhtml"
                        return;
                    }

                   
             
                }, ( err ) => {
                    console.log( 'Error: ' + err );
                    this.incorrectCredentials = true;
                    this.router.navigate( ['/login'] );
                } );

    
    }

    //  loginCheck():string {      
    //      return this.cas.loginCheck();
    //  }


}
