import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { AuthGuard } from './AuthGuard';
import { CounselingAPIService } from '../CounselingAPIService';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

const routes: Routes = [
  { path: 'dashboard/user', component: UserDashboardComponent, canActivate: [ AuthGuard ]},
];

@NgModule({
  declarations: [
    UserDashboardComponent
],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CounselingAPIService, AuthGuard,
    { provide: LocationStrategy, useClass: HashLocationStrategy }
    ],
})
export class UserBoardRoutingModule { }
