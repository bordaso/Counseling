import { Component, OnInit } from '@angular/core';
import { CounselingAPIService } from 'src/app/CounselingAPIService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {

  constructor(private cas:CounselingAPIService,  private router: Router) { }

  ngOnInit() {
  }
  
  
  hide():boolean {
      return false;
      }

      logout(){
        return this.cas.logout().subscribe(
          ( response: string ) => {
              this.router.navigate( ['home'] );
          }, ( err ) => {
              console.log( 'Error: ' + err );
              this.router.navigate( ['/home'] );
          } );
      }

}
