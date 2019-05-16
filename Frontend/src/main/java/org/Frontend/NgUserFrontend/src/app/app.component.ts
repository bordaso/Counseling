import { Component, Input } from '@angular/core';
import { Router } from "@angular/router";
import { UserDashboardComponent } from "src/app/userBoard/user-dashboard/user-dashboard.component";
import { BookingsComponent } from './userBoard/bookings/bookings.component';
import { MessagesComponent } from './userBoard/messages/messages.component';
import { ProfileComponent } from './userBoard/profile/profile.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  
  constructor(private router: Router) {};
  
     @Input()  notTheUserDashboard = true;
     
     onActivate(elementRef) {
         if(elementRef instanceof UserDashboardComponent 
          ||  elementRef instanceof BookingsComponent
          ||  elementRef instanceof MessagesComponent
          ||  elementRef instanceof ProfileComponent
          ){
             
         //elrejti a headert az app.component.html-ben hogy a UserDashboardComponentben más kerülhessen oda
         //a függvényhívás nem volna szükséges, de az érdekesség miatt meghagytam             
         //this.notTheUserDashboard = elementRef.hide();
         
         this.notTheUserDashboard = false;
         return;
         }
         this.notTheUserDashboard = true;
     }
     
}
