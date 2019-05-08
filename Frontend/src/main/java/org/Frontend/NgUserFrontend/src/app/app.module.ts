import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { ServicesComponent } from './services/services.component';
import { TeamComponent } from './team/team.component';
import { LoginComponent } from './login/login.component';
import { LocationStrategy } from "@angular/common";
import { HashLocationStrategy } from "@angular/common";


/*             "outputPath": "dist/NgUserFrontend", */

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
//                        {
//                        path: 'protected',
//                        component: ProtectedComponent,
//                        canActivate: [ LoggedInGuard ]
//                        },
//
//                        // nested
//                        {
//                        path: 'products',
//                        component: ProductsComponent,
//                        children: childRoutes
//                        }
                        ];



@NgModule( {
    declarations: [
        AppComponent,
        HomeComponent,
        AboutComponent,
        ContactComponent,
        ServicesComponent,
        TeamComponent,
        LoginComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        RouterModule.forRoot(routes) // <-- routes
        
         // added this for our child module
//         ,ProductsModule
    ],
    providers: [
{ provide: LocationStrategy, useClass: HashLocationStrategy }
],
    bootstrap: [AppComponent]
} )





export class AppModule { 
    
    
    
    
    
    
    
    
    
    
    
}
