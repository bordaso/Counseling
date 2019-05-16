import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserBoardModule } from './userBoard/user-board.module';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CustomMaterialModule } from './custom-material/custom-material.module';




@NgModule( {
    declarations: [
        AppComponent
    ],
    imports: [
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
