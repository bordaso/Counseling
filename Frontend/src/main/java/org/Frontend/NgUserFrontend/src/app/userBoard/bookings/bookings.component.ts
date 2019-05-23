import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatCalendarCellCssClasses, MatPaginator, MatTableDataSource } from '@angular/material';
import { Subscription } from 'rxjs';
import { CounselingAPIService } from 'src/app/CounselingAPIService';


export interface Booking {
  id: number;
  title: string;
  start: string;
  end: string;
  room: string;
  details?: BookingDetails;
}

export interface BookingDetails {
  id: number;
  bookingID: number;
  response: string;
}


@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css']
})
export class BookingsComponent implements OnInit {
  displayedColumns: string[] = ['title', 'start', 'end', 'room', 'response', 'actions'];
  dataSourceBooking = new MatTableDataSource<Booking>();
  selectionBooking = new SelectionModel<Booking>(true, [], true);

  datesToHighlight = ["2019-05-14T18:30:00.000Z", "2019-05-15T18:30:00.000Z", "2019-05-16T18:30:00.000Z", "2019-05-20T18:30:00.000Z", "2019-05-28T18:30:00.000Z"];


  @ViewChild(MatPaginator) paginator: MatPaginator;

  subscription: Subscription;
  dateTimeNOW = new Date()

  selectedDateVal: any;

  onSelectDate(event) {
    console.log(event);
    this.selectedDateVal = event;
  }

  onSelectRow(row: Booking) {
    console.log(row);
    this.selectionBooking.toggle(row);
  }

  ngOnInit() {
    this.dataSourceBooking.paginator = this.paginator;
    this.subscription = this.cas.bookingsService(this.dataSourceBooking);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  constructor(private cas: CounselingAPIService) { }
  


  acceptAppointment(row: Booking) {    
    this.cas.bookingsResponseService(row.details.id, 1);  
    this.cas.bookingsServiceDirectCall(this.dataSourceBooking);   
    this.proposedAppointment(row, true);
    console.log("Accepted appointment "+row);
  }

  rejectAppointment(row: Booking) {
    this.cas.bookingsResponseService(row.details.id, 0);
    this.cas.bookingsServiceDirectCall(this.dataSourceBooking);  
    this.proposedAppointment(row, true);
    console.log("Rejected appointment "+row);
  }

  dateClass() {

    return (date: Date): MatCalendarCellCssClasses => {

      const highlightDate = this.datesToHighlight
        .map(strDate => new Date(strDate))
        .some(d => d.getDate() === date.getDate() && d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear());

      this.datesToHighlight.push("2019-05-30T18:30:00.000Z");

      if(highlightDate){
        return 'special-date';
      }

      //this.datesToHighlight.push("2019-05-31T18:30:00.000Z");


      var dNew:Date=new Date("2019-05-31T18:30:00.000Z");

      if(dNew.getDate() === date.getDate() && dNew.getMonth() === date.getMonth() && dNew.getFullYear() === date.getFullYear()){
        return 'special-date-later';
      }

      // return highlightDate ? 'special-date' : '';
      return '';
    };
  }


  proposedAppointment(row: Booking, doItNow?:boolean): boolean {

    if(doItNow === true){
      return false;
    }

    if (row.details.response === "NO_RESPONSE") {
      return true;
    }
    return false;
  }

}

























