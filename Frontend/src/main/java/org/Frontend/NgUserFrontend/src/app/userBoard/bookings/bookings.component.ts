import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatCalendar, MatCalendarCellCssClasses, MatTableDataSource, MatPaginator } from '@angular/material';
import { Moment } from 'moment';
import { Subscription, timer, pipe, Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators'

import { SelectionModel } from '@angular/cdk/collections';
import { HttpClient } from '@angular/common/http';
import { CounselingAPIService } from 'src/app/CounselingAPIService';


export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}


export interface Booking {
  id: number;
  title: string;
  start: string;
  end: string;
  room: string;
}

export interface BookingDetails {
  id: number;
  bookingID: number;
  response: string;
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
export class BookingsComponent implements OnInit, AfterViewInit {
 // displayedColumns: string[] = ['select', 'position', 'name', 'weight', 'symbol', 'actions' ];
 displayedColumns: string[] = ['title', 'start', 'end', 'response', 'actions' ];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
  selection = new SelectionModel<PeriodicElement>(true, []);

  subscription: Subscription;
statusText: string;

ngOnInit() {
  this.dataSource.paginator = this.paginator;

  this.subscription = timer(0, 10000).pipe(
    switchMap(() => this.http
    .post<string>( 'http://localhost:54321/rest/service/all/bookings', `` ,{ headers: { 'Content-Type': 'application/json' }, responseType: 'json', withCredentials: true } ))
  ).subscribe(result =>{
    
 
var index = result.indexOf( "#" );  
var indexEnd = result.indexOf( "##" );  

var slicedBooking = result.slice(0, index); 
var slicedBookingDetails = result.slice(index+1, indexEnd); 

/* var bookingJsonString = JSON.stringify(eval("(" + slicedBooking + ")"));
var bookingDetailsJsonString = JSON.stringify(eval("(" + slicedBookingDetails + ")"));
console.log(JSON.parse(bookingJsonString));
console.log(JSON.parse(bookingDetailsJsonString)); */


let booking: Booking = JSON.parse(slicedBooking).Booking;
let bookingDetails: BookingDetails = JSON.parse(slicedBookingDetails).BookingDetails;
    
    console.log(booking.start);
    console.log(bookingDetails.response);


let bookingMapping = new Map<Booking, BookingDetails>();

bookingMapping.set(booking, bookingDetails);

console.log(bookingMapping);
    
     });





}

ngOnDestroy() {
  this.subscription.unsubscribe();
}

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private http: HttpClient, private cas: CounselingAPIService) {}

  selectedDate: any;
  name = 'Angular 6';

  onSelect(event){
    console.log(event);
    this.selectedDate= event;
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


    proposedAppointment(row: PeriodicElement):boolean{

      if(row.name==="Neon" || row.name==="Boron"){
        return true;
      }
      return false;
    }
    





    exampleDatabase: ExampleHttpDao | null;
    dataSourcee = new MatTableDataSource();
  
    resultsLength = 0;
    isLoadingResults = false;
    isRateLimitReached = false;
  

  
    ngAfterViewInit() {
  /*     this.exampleDatabase = new ExampleHttpDao(this.http);
  
      merge(this.sort.sortChange, this.paginator.page)
        .pipe(
          startWith({}),
          switchMap(() => {
            this.isLoadingResults = true;
            return this.exampleDatabase!.getRepoIssues(
              this.sort.active, this.sort.direction, this.paginator.pageIndex);
          }),
          map(data => {
            // Flip flag to show that loading has finished.
            this.isLoadingResults = false;
            this.isRateLimitReached = false;
            this.resultsLength = data.total_count;
  
            return data.items;
          }),
          catchError(() => {
            this.isLoadingResults = false;
            // Catch if the GitHub API has reached its rate limit. Return empty data.
            this.isRateLimitReached = true;
            return observableOf([]);
          })
        ).subscribe(data => this.dataSource.data = data); */
    }
  }
  
  export interface GithubApi {
    items: GithubIssue[];
    total_count: number;
  }
  
  export interface GithubIssue {
    created_at: string;
    number: string;
    state: string;
    title: string;
  }
  
  /** An example database that the data source uses to retrieve data for the table. */
  export class ExampleHttpDao {
    constructor(private http: HttpClient) {}
  
    getRepoIssues(sort: string, order: string, page: number): Observable<GithubApi> {
      const href = 'https://api.github.com/search/issues';
      const requestUrl =
          `${href}?q=repo:angular/material2&sort=${sort}&order=${order}&page=${page + 1}`;
  
      return this.http.get<GithubApi>(requestUrl);
    }

  }























  

