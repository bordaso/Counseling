import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { Input } from "@angular/core";
import { LoginComponent } from "src/app/login/login.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
    
    //[varvar]="toHide($event)"
  
  constructor(private router: Router) {
     };
  
     @Input()  varvar = true;
     
     toHide(hidden: boolean):boolean {
         console.log("xxxx"+ hidden);
         return hidden;
       }
     
     
     onActivate(elementRef) {
         if(elementRef instanceof LoginComponent){
         this.varvar = elementRef.hide();//  this.varvar ==  elementRef.hide2()? !this.varvar : 
         return;
         }
         this.varvar = true;
     }
     
//     onSubmit() {
//         this.router.navigate(['/login.html'])
//      }
     
}
