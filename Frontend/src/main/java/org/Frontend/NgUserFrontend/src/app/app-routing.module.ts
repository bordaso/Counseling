import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from "src/app/about/about.component";
import { ContactComponent } from "src/app/contact/contact.component";
import { HomeComponent } from "src/app/home/home.component";
import { LoginComponent } from "src/app/login/login.component";
import { ServicesComponent } from "src/app/services/services.component";
import { TeamComponent } from "src/app/team/team.component";
import { CounselingAPIService } from './CounselingAPIService';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

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
                        { path: 'login', component: LoginComponent }
//
//                        // nested
//                        {
//                        path: 'products',
//                        component: ProductsComponent,
//                        children: childRoutes
//                        }
                        ];


@NgModule({
  declarations: [
    HomeComponent,
    AboutComponent,
    ContactComponent,
    ServicesComponent,
    TeamComponent,
    LoginComponent
],
  imports: [RouterModule.forRoot(routes),       
    BrowserModule,
    CommonModule,
    HttpClientModule,
    FormsModule,],
  exports: [RouterModule],
  providers: [CounselingAPIService,
    { provide: LocationStrategy, useClass: HashLocationStrategy }
    ],
})
export class AppRoutingModule { }
