import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { Input } from "@angular/core";
import { LoginComponent } from "src/app/login/login.component";
import { UserDashboardComponent } from "src/app/user-dashboard/user-dashboard.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  
  constructor(private router: Router) {};
  
     @Input()  notTheUserDashboard = true;
     
     onActivate(elementRef) {
         if(elementRef instanceof UserDashboardComponent){
             
         //elrejti a headert az app.component.html-ben hogy a UserDashboardComponentben más kerülhessen oda
         //a függvényhívás nem volna szükséges, de az érdekesség miatt meghagytam
             
         this.notTheUserDashboard = elementRef.hide();
         return;
         }
         this.notTheUserDashboard = true;
     }
     
}
