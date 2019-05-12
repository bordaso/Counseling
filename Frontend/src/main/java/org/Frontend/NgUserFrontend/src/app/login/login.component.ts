import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RequestOptions } from "@angular/http";
import { Input } from "@angular/core";
import { map, filter, catchError, mergeMap } from 'rxjs/operators';


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
  }
  
  submit(): void {
      console.log(this.inId+" "+this.inPw+" "+this.isChecked );
      
    let data = {
              "id": this.inId,
              "pw": this.inPw
          }; 
      
    const formData = new FormData();
    formData.append('id', this.inId);
    formData.append('pw', this.inPw);
    formData.append('empswitch', this.isChecked+"");
    
    let body = new URLSearchParams();
    body.set('id', this.inId);
    body.set('pw', this.inPw);
    body.set('empswitch', this.isChecked+"");
    
    console.log(body+" here")
    
    let body2 = `id=${this.inId}&pw=${this.inPw}&empswitch=${this.isChecked}`;

      this.http
      .post('http://localhost:54321/dashboard/login',body2, {headers : { 'Content-Type': 'application/x-www-form-urlencoded' }, responseType: 'json' , withCredentials: true})
    //  .map(response => response.json())
      .pipe(
            map(responce=> responce )
        )
      .subscribe(
          (response: Response) => {
              
              console.log('resp: ' + response );
              
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
                 .get('http://localhost:54321/rest/service/logincheck', {withCredentials: true})
                 .subscribe(
                     (response: Response) => {
                         
                         console.log('resp: ' + response);
                         
                         
                 }, (err) => {
                     console.log('Error: ' + err);
                 });
  }

  
  
//  authenticate(username: string, password: string) {
//      var body = `{"username":"${username}","password":"${password}"}`;
//      var headers = new Headers();
//      headers.append('Content-Type', 'application/json');
//      let options = new RequestOptions({ headers: headers, withCredentials: true });
//
//      return this._http
//                 .post('http://demo...', body, options)
//                 .subscribe(
//                     (response: Response) => {
//                     this.doSomething(response);
//                 }, (err) => {
//                     console.log('Error: ' + err);
//                 });
//  }
//  
  
  
}
