import { Component, OnInit, ViewChild } from '@angular/core';
import { MatCalendar, MatCalendarCellCssClasses, MatTableDataSource, MatPaginator } from '@angular/material';
import { Moment } from 'moment';

import { SelectionModel } from '@angular/cdk/collections';


export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];


@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css']
})
export class BookingsComponent implements OnInit {
 // displayedColumns: string[] = ['select', 'position', 'name', 'weight', 'symbol', 'actions' ];
 displayedColumns: string[] = ['position', 'name', 'weight', 'symbol', 'actions' ];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
  selection = new SelectionModel<PeriodicElement>(true, []);

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor() { }

  selectedDate: any;
  name = 'Angular 6';

  onSelect(event){
    console.log(event);
    this.selectedDate= event;
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
}

acceptAppointment(row: PeriodicElement){
  console.log("Accept: ");
  console.log(row);
}

rejectAppointment(row: PeriodicElement){
  console.log("Reject: ");
  console.log(row);
}

  selectedDate2: any;

  datesToHighlight = ["2019-01-22T18:30:00.000Z", "2019-01-22T18:30:00.000Z", "2019-01-24T18:30:00.000Z", "2019-01-28T18:30:00.000Z", "2019-01-24T18:30:00.000Z", "2019-01-23T18:30:00.000Z", "2019-01-22T18:30:00.000Z", "2019-01-25T18:30:00.000Z"];

  datesToHighlight2 = ["2019-05-14T18:30:00.000Z", "2019-05-15T18:30:00.000Z", "2019-05-16T18:30:00.000Z", "2019-05-20T18:30:00.000Z", "2019-05-28T18:30:00.000Z"];

  datesToHighlight3 = ["2019-05-14T18:30:00.000Z", "2019-05-15T18:30:00.000Z", "2019-05-16T18:30:00.000Z", "2019-05-20T18:30:00.000Z"];

 dateTimeNOW = new Date()

  onSelect2(event){
    console.log(event);
    this.selectedDate2 = event;
  }

  dateClass() {
    return (date: Date): MatCalendarCellCssClasses => {
      const highlightDate = this.datesToHighlight
        .map(strDate => new Date(strDate))
        .some(d => d.getDate() === date.getDate() && d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear());

        const highlightDate2 = this.datesToHighlight2
        .map(strDate => new Date(strDate))
        .some(d => d.getDate() === date.getDate() && d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear());

        const highlightDate3 = this.datesToHighlight3
        .map(strDate => new Date(strDate))
        .some(d => 
         (d.getDate() < this.dateTimeNOW.getDate() && d.getMonth() <  this.dateTimeNOW.getMonth() && d.getFullYear() <  this.dateTimeNOW.getFullYear()) );
      
         console.log(highlightDate3+" xx ")
      //return highlightDate || highlightDate2 ? 'special-date' : '';

      this.datesToHighlight2.push("2019-05-30T18:30:00.000Z");

      return highlightDate2 ? highlightDate3? 'special-date-later':'special-date' : '';
    };
  }

    /** Whether the number of selected elements matches the total number of rows. */
    isAllSelected() {
      const numSelected = this.selection.selected.length;
      const numRows = this.dataSource.data.length;
      return numSelected === numRows;
    }
  
    /** Selects all rows if they are not all selected; otherwise clear selection. */
    masterToggle() {
      this.isAllSelected() ?
          this.selection.clear() :
          this.dataSource.data.forEach(row => this.selection.select(row));
    }
  
    /** The label for the checkbox on the passed row */
    checkboxLabel(row?: PeriodicElement): string {
      if (!row) {
        return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
      }
      return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;
    }

    proposedAppointment(row: PeriodicElement):boolean{

      if(row.name==="Neon" || row.name==="Boron"){
        return true;
      }
      return false;
    }
    




}




  

