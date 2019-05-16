import { NgModule } from '@angular/core';
//import { MatButtonModule, MatDatepickerModule } from '@angular/material';
import { MatMomentDateModule } from "@angular/material-moment-adapter";


import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import {
    MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule,
    MatListModule, MatSelectModule, MatRadioModule, MatGridListModule, MatInputModule, MatDatepickerModule, MatNativeDateModule,MatCardModule
} from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [],
  imports: [
    MatButtonModule,
    MatDatepickerModule,
    MatMomentDateModule,
    
    NoopAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatSelectModule,
    MatRadioModule,
    MatGridListModule,
    MatInputModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatCardModule,
    
    FlexLayoutModule
  ],
  exports: [
    MatButtonModule,
    MatDatepickerModule,
    MatMomentDateModule,
    
    NoopAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatSelectModule,
    MatRadioModule,
    MatGridListModule,
    MatInputModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatCardModule,

    FlexLayoutModule
  ]
})
export class CustomMaterialModule { }
