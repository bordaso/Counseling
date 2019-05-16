import { Component, OnInit, ViewChild } from '@angular/core';
import { MatCalendar } from '@angular/material';
import { Moment } from 'moment';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css']
})
export class BookingsComponent implements OnInit {

  constructor() { }
/* 
  @ViewChild('calendar')
  calendar: MatCalendar<Moment>;

  selectedDate: Moment;

  monthSelected(date) {
    alert(`Selected: ${date}`);
  } */

  selectedDate: any;
  name = 'Angular 6';

  onSelect(event){
    console.log(event);
    this.selectedDate= event;
  }

  ngOnInit() {
  }

}
