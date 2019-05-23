import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { CanActivate } from "@angular/router";
import { ActivatedRouteSnapshot } from "@angular/router";
import { RouterStateSnapshot } from "@angular/router";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, timer } from "rxjs";
import { Promise } from "q";
import { Booking, BookingDetails } from "./userBoard/bookings/bookings.component";
import { switchMap } from "rxjs/operators";
import { MatTableDataSource } from "@angular/material";




@Injectable()
export class CounselingAPIService {


    constructor( private http: HttpClient ) { }

    //constructor( private cookieService:CookieService) {};


    loginCheck(){//: string {
        
      

        return this.http
            .get<string>( 'http://localhost:54321/rest/service/logincheck', { withCredentials: true } );
        /*    .subscribe(
            ( response: string ) => {

              //  console.log( 'resp: ' + response );
               // this.returnValLC = response;

            }, ( err ) => {
                console.log( 'Error: ' + err );
               // this.returnValLC ="error occured in LC"
            } );
*/
      //  return this.returnValLC;
    }

    loginService( inId: string, inPw: string, isChecked: boolean ){//: string {
        
        let body = `id=${inId}&pw=${inPw}&empswitch=${isChecked}`;

        //response a CustomSuccessHandlerbol
        
        return this.http
            .post<string>( 'http://localhost:54321/dashboard/login', body, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, responseType: 'json', withCredentials: true } )
        /*    .subscribe(
            ( response: string ) => {
                
                this.returnValL = response;                
            }, ( err ) => {
                console.log( 'Error: ' + err );
                this.returnValL = "error occured in L";
            } 
        
 
        return this.returnValL;
*/
    }
   
    bookingsServiceDirectCall(dataSourceBooking: MatTableDataSource<Booking>){
       return this.http
        .post<string>('http://localhost:54321/rest/service/all/bookings', ``, { headers: { 'Content-Type': 'application/json' }, responseType: 'json', withCredentials: true })        
        .subscribe(result => {
      
      
          var resultBookings = this.utilBookingsSlicer(result); 
    
    
          dataSourceBooking.data = resultBookings;
    
    
    
        });
      }

    bookingsService(dataSourceBooking: MatTableDataSource<Booking>){
        return timer(0, 10000).pipe(
            switchMap(() => 
            this.http.post<string>('http://localhost:54321/rest/service/all/bookings', ``, { headers: { 'Content-Type': 'application/json' }, responseType: 'json', withCredentials: true })
          // this.bookingsServiceCall()   
           )
          ).subscribe(result => {
      
      
            var resultBookings = this.utilBookingsSlicer(result);
      
            /* let booking2: Booking =  {
                "id" : 2,
                "title" : "booking3",
                "start" : "2019-04-20T14:00",
                "end" : "2019-04-21T15:00",
                "room" : "room4",
                "details": {
                  "id" : 4,
                  "bookingID" : 2,
                  "response" : "ACCEPTED"
                }
              };*/
      
      
      
            dataSourceBooking.data = resultBookings;
      
      
      
          });
      



    }

    bookingsResponseService(bdid: number, bdresponse: number){

       /*  const httpOptions = {
            headers: new HttpHeaders({      
                'bdid': <string><any>bdid ,
                'bdresponse': <string><any>bdresponse,                
                'responseType': "json", 
                'withCredentials': "true"
            })
          };

          return this.http
          .post<string>( 'http://localhost:54321/rest/service/all/bookingresponse', ``,httpOptions )
          .subscribe(result => console.log(result));
 */

 var bdidS:string=<string><any>bdid;
 var bdresponseS:string=<string><any>bdresponse;

 console.log(bdidS+" xx "+bdresponseS);

        return this.http
        .post<string>( 'http://localhost:54321/rest/service/all/bookingresponse', ``,{ headers: { 'bdid': bdidS+"",'bdresponse': bdresponseS+"", 'Content-Type': 'application/x-www-form-urlencoded'}, responseType: 'json', withCredentials: true } )
        .subscribe(result => console.log(result));
    }

    logout(){
        return this.http
            .get<string>( 'http://localhost:54321/logout', { withCredentials: true } );
    }

















    private utilBookingsSlicer(inPut: string): Booking[] {

        //console.log((inPut.match(/##/g) || []).length+" NUMBER OF ##")
    
        /* var bookingJsonString = JSON.stringify(eval("(" + slicedBooking + ")"));
      var bookingDetailsJsonString = JSON.stringify(eval("(" + slicedBookingDetails + ")"));
      console.log(JSON.parse(bookingJsonString));
      console.log(JSON.parse(bookingDetailsJsonString)); */
    
        var numberOfElements: number = (inPut.match(/##/g) || []).length;
    
        var listOfBookingAndDetails: string[] = [];
    
        var tempHolder = inPut;
    
        var endResult: Booking[] = [];
    
        for (var i: number = 0; i < numberOfElements; i++) {
    
          var index = tempHolder.indexOf("##");
    
          listOfBookingAndDetails.push(tempHolder.slice(0, index));
    
          tempHolder = tempHolder.slice(index + 2, tempHolder.length);
        }
    
    
        endResult = this.utilBookingDetailsSlicer(listOfBookingAndDetails);
    
    
        return endResult;
      }
    
    
      private utilBookingDetailsSlicer(input: string[]): Booking[] {
    
        var resultBookings: Booking[] = [];
    
        for (var i: number = 0; i < input.length; i++) {
    
          var element = input[i];
    
          var index = element.indexOf("#");
    
          var slicedBooking = element.slice(0, index);
          var slicedBookingDetails = element.slice(index + 1);
    
          let booking: Booking = JSON.parse(slicedBooking).Booking;
          let bookingDetails: BookingDetails = JSON.parse(slicedBookingDetails).BookingDetails;
    
          booking.details = bookingDetails;
    
          resultBookings.push(booking);
        }
    
        return resultBookings;
      };


}
