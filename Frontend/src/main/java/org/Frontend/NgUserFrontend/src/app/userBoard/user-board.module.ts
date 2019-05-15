import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserDashboardComponent } from "src/app/userBoard/user-dashboard/user-dashboard.component";

import { UserBoardRoutingModule } from './user-board-routing.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    UserBoardRoutingModule
  ]
})
export class UserBoardModule { }
