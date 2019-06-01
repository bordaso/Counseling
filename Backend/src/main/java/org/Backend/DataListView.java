package org.Backend;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.User;
import org.Backend.Enums.UserType;
import org.Backend.Services.EmployeeService;
import org.Backend.Services.PatientService;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DataListView extends SpringBeanAutowiringSupport implements Serializable  {
    
	
   private List<User> myUsers;
   
   private List<Bookings> myUsersBookings = new ArrayList<Bookings>();
   
   private Map<User, Bookings> myUsersBookingsMap = new HashMap<User, Bookings>();
   
   private List<UserType> types = new ArrayList<UserType>();
   
   private List<User> myUsersSelected = new ArrayList<User>();
	
   private List<Car> cars1;
    
   private List<Car> cars2;
    
   private List<Car> cars3;
    
   private Car selectedCar;
   
   private String testt = "HELLLOOOO";   
   
   @Autowired
   private PatientService ps;
   
   @Autowired
   private EmployeeService es;
   
   
    
   public String getTestt() {
	return testt;
}

public void setTestt(String testt) {
	this.testt = testt;
}

@Autowired
   private CarService service;

@Autowired
private EmployeeService empService;
   

public void carInTheRow(Car selectedCar) {
	   System.out.println(selectedCar);
	
}
    
   @PostConstruct
   public void init() { 
	   
	   myUsers = empService.selectMyUsers();
	   
	   types = Arrays.asList(UserType.values());
	   
	   
       cars1 = service.createCars(10);
     //  cars2 = service.createCars(5);
       cars2 = new ArrayList<Car>();
       cars3 = service.createCars(50);
   }

   public List<Car> getCars1() {
       return cars1;
   }

   public List<Car> getCars2() {
       return cars2;
   }

   public List<Car> getCars3() {
       return cars3;
   }    

   
   
   
   
   
   public List<UserType> getTypes() {
	return types;
}

public void setTypes(List<UserType> types) {
	this.types = types;
}

public List<User> getMyUsersSelected() {
	return myUsersSelected;
}

public void setMyUsersSelected(List<User> myUsersSelected) {
	this.myUsersSelected = myUsersSelected;
}

public List<User> getMyUsers() {
	return myUsers;
}

public void setMyUsers(List<User> myUsers) {
	this.myUsers = myUsers;
}

public void setCars2(List<Car> cars2) {
	this.cars2 = cars2;
}

public void setService(CarService service) {
       this.service = service;
   }

   public Car getSelectedCar() {
	   System.out.println(selectedCar+" get selected car");
       return selectedCar;
   }

   public void setSelectedCar(Car selectedCar) {
	   System.out.println(selectedCar+" set selected car");
       this.selectedCar = selectedCar;
   }    
   
   //public List<Bookings> getMyUsersBookings() {

	   public List<Map.Entry<User, Bookings>> getMyUsersBookings() {

		    Set<Map.Entry<User, Bookings>> bookingsSet = myUsersBookingsMap.entrySet();
		    
		    return new ArrayList<Map.Entry<User, Bookings>>(bookingsSet);

	
	   
	   
//	return myUsersBookings;
}

   public void setMyUsersBookings(List<Bookings> myUsersBookings) {
	this.myUsersBookings = myUsersBookings;
}
   
public Map<User, Bookings> getMyUsersBookingsMap() {
	return myUsersBookingsMap;
}

public void setMyUsersBookingsMap(Map<User, Bookings> myUsersBookingsMap) {
	this.myUsersBookingsMap = myUsersBookingsMap;
}

public void onUserSelect(SelectEvent event) {
	   System.out.println((User) event.getObject()+" SELECT");
	   
	   User selUser = (User) event.getObject();
	   
	   if(!myUsersSelected.contains(selUser)) {
		   myUsersSelected.add(selUser);
	   }	   
	  
	   
	   bookingsSetup(selUser);
	   

	   
   }

   private void bookingsSetup(User u) {   

		   Map<Bookings, BookingDetails> bookingMap =  u.getType() == UserType.PATIENT? ps.patientFindAllUpcomingBookings(u.getUsername()) : es.employeeFindAllUpcomingBookings(u.getUsername());
		   
		   bookingSorter(bookingMap, u);	
}

private void bookingSorter(Map<Bookings, BookingDetails> bookingMap, User u) {
	
	   FacesContext context = FacesContext.getCurrentInstance();
	   ScheduleView sBean = context.getApplication().evaluateExpressionGet(context, "#{scheduleView}", ScheduleView.class);
	   Date selectedStartDate = sBean.getEvent().getStartDate();
	   Date selectedEndDate = sBean.getEvent().getEndDate();

	   for(Bookings bks : bookingMap.keySet()) {
		   
		   LocalDate selectedStartLocalDateTime = LocalDateTime.ofInstant(selectedStartDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
		   LocalDate selectedEndLocalDateTime = LocalDateTime.ofInstant(selectedEndDate.toInstant(), ZoneId.systemDefault()).toLocalDate();				   
		   
		   if(
			(	   bks.getStart().toLocalDate().isEqual(selectedStartLocalDateTime) || bks.getEnd().toLocalDate().isEqual(selectedStartLocalDateTime) ||
				  ( bks.getStart().toLocalDate().isBefore(selectedStartLocalDateTime) &&  bks.getEnd().toLocalDate().isAfter(selectedStartLocalDateTime) )
				   ||
				   bks.getStart().toLocalDate().isEqual(selectedEndLocalDateTime) || bks.getEnd().toLocalDate().isEqual(selectedEndLocalDateTime) ||
			      ( bks.getStart().toLocalDate().isBefore(selectedEndLocalDateTime) &&  bks.getEnd().toLocalDate().isAfter(selectedEndLocalDateTime) )
			)
			&&
			!myUsersBookingsMap.keySet().contains(u)
			  ) 
		   {
			   
			   myUsersBookings.add(bks);
			   myUsersBookingsMap.put(u, bks);
			   
		   }
	   }
	
}

public void onUserUnselect(UnselectEvent event) {
       System.out.println((User) event.getObject()+" UNSELECT");
       
       User selUser = (User) event.getObject();
       
       myUsersSelected.remove(selUser);
       myUsersBookingsMap.remove(selUser);

       
//       Map<Bookings, BookingDetails> bookingMap =  selUser.getType() == UserType.PATIENT? ps.patientFindAllUpcomingBookings(selUser.getUsername()) : es.employeeFindAllUpcomingBookings(selUser.getUsername());
//       
//       BiPredicate<Bookings, Bookings> predicate = (p, f) -> p.equals(f);  
//       
//       List<Bookings> filtered = myUsersBookings
//               .parallelStream()
//               .filter(p -> bookingMap.keySet().parallelStream().anyMatch(s -> predicate.test(p, s)))
//               .collect(Collectors.toCollection(ArrayList::new));
//       
//       myUsersBookings.removeAll(filtered);
   }

public void emptyBookings() {
    
    myUsersBookings.clear();
    myUsersBookingsMap.clear();
    myUsersSelected.clear();


}

   
}