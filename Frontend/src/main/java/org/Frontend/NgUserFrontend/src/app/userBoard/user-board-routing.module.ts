import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { AuthGuard } from './AuthGuard';
import { CounselingAPIService } from '../CounselingAPIService';
import { LocationStrategy, HashLocationStrategy, CommonModule } from '@angular/common';
import { BookingsComponent } from './bookings/bookings.component';
import { MessagesComponent } from './messages/messages.component';
import { ProfileComponent } from './profile/profile.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CustomMaterialModule } from '../custom-material/custom-material.module';


const routes: Routes = [
  { path: 'dashboard/user', component: UserDashboardComponent, canActivate: [AuthGuard], 
  children:[

    {
      path: '',
      canActivateChild: [AuthGuard],
      children: [
        { path: 'bookings', component: BookingsComponent },
        { path: 'messages', component: MessagesComponent },
        { path: 'profile', component: ProfileComponent }
      ]
    }



    
  ] }

  


];

@NgModule({
  declarations: [
    UserDashboardComponent,
    BookingsComponent,
    MessagesComponent,
    ProfileComponent
  ],
  imports: [RouterModule.forChild(routes), 
    BrowserAnimationsModule,
    CustomMaterialModule,
    BrowserModule,
    CommonModule,
    HttpClientModule,
    FormsModule],
  exports: [RouterModule],
  providers: [CounselingAPIService, AuthGuard,
    { provide: LocationStrategy, useClass: HashLocationStrategy }
  ],
})
export class UserBoardRoutingModule { }
