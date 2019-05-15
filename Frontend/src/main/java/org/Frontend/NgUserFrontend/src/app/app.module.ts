import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserBoardModule } from './userBoard/user-board.module';
import { CommonModule } from '@angular/common';



@NgModule( {
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        CommonModule,
        HttpClientModule,
        FormsModule,
        UserBoardModule,
        AppRoutingModule
    ],
    bootstrap: [AppComponent]
} )





export class AppModule { 
    
    
    
    
    
    
    
    
    
    
    
}
