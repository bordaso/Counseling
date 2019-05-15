import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from "src/app/home/home.component";
import { AboutComponent } from "src/app/about/about.component";
import { ServicesComponent } from "src/app/services/services.component";
import { TeamComponent } from "src/app/team/team.component";
import { ContactComponent } from "src/app/contact/contact.component";
import { LoginComponent } from "src/app/login/login.component";
import { UserDashboardComponent } from "src/app/user-dashboard/user-dashboard.component";
import { AuthGuard } from "src/app/AuthGuard";

const routes: Routes = [
                        // basic routes
                        { path: '', redirectTo: 'home', pathMatch: 'full' },
                        { path: 'home', component: HomeComponent },
                        { path: 'about', component: AboutComponent },
                        { path: 'services', component: ServicesComponent },
                        { path: 'team', component: TeamComponent },
                        { path: 'contact', component: ContactComponent },
                        { path: 'contactus', redirectTo: 'contact' },

                        // authentication demo
                        { path: 'login', component: LoginComponent },
                        { path: 'dashboard/user', component: UserDashboardComponent, canActivate: [ AuthGuard ]},
//
//                        // nested
//                        {
//                        path: 'products',
//                        component: ProductsComponent,
//                        children: childRoutes
//                        }
                        ];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
