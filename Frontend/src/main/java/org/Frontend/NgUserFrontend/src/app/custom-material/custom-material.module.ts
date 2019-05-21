import { NgModule } from '@angular/core';
//import { MatButtonModule, MatDatepickerModule } from '@angular/material';
import { MatMomentDateModule } from "@angular/material-moment-adapter";


import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import {MatTableModule,
    MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatCheckboxModule, MatPaginatorModule,
    MatListModule, MatSelectModule, MatRadioModule, MatGridListModule, MatInputModule, MatDatepickerModule, MatNativeDateModule,MatCardModule
} from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { CounselingAPIService } from '../CounselingAPIService';

@NgModule({
  declarations: [],
  imports: [
    MatTableModule,
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
    MatCheckboxModule,
    MatPaginatorModule,
    
    
    FlexLayoutModule
  ],
  exports: [
    MatTableModule,
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
    MatCheckboxModule,
    MatPaginatorModule,

    FlexLayoutModule
  ]
})
export class CustomMaterialModule { }
