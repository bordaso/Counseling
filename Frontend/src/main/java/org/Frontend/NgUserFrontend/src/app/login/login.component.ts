import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RequestOptions } from "@angular/http";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit() {
      console.log("init");
      this.checkLogin();
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
