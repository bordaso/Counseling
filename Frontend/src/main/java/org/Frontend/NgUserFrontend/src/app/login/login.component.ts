import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RequestOptions } from "@angular/http";
import { Input } from "@angular/core";
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { Output } from "@angular/core";
import { EventEmitter } from "@angular/core";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    
    @Input() inId = ""; 
    @Input() inPw = ""; 
    @Input() isChecked = false; 
    

  constructor(private http: HttpClient) { }
  
  ngOnInit() {
      console.log("init");
      this.checkLogin();
      this.hide();
  }

  
  hide():boolean {
    return true;
    }
  

  
  
  submit(): void {
   //   console.log(this.inId+" "+this.inPw+" "+this.isChecked );
      this.hide();
      

    
    let body2 = `id=${this.inId}&pw=${this.inPw}&empswitch=${this.isChecked}`;

      this.http
      .post<string>('http://localhost:54321/dashboard/login',body2, {headers : { 'Content-Type': 'application/x-www-form-urlencoded' }, responseType: 'json' , withCredentials: true})
    //  .map(response => response.json())
      .pipe(
            map(responce=> responce )
        )
      .subscribe(
          (response: string) => {
              
              console.log('resp: ' + response[0] );
              
              let ab : any;
              
              ab = response;
              
              let bc : string;
              
              bc = <string>ab;
              
           //  let response2 = response.clone;
             
             console.log('resp: ' + bc[0]);
              
              
              
      }, (err) => {
          console.log('Error: ' + err);
      });
    
  }
  
  
  
  
  
  checkLogin() {
      
          //
      return this.http
                 .get<string>('http://localhost:54321/rest/service/logincheck', {withCredentials: true})
                 .subscribe(
                     (response: string) => {
                         
                         console.log('resp: ' + response);
                         return response;
                         
                 }, (err) => {
                     console.log('Error: ' + err);
                 });
  }

  
}
