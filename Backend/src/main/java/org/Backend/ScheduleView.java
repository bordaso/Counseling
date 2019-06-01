package org.Backend;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.User;
import org.Backend.Enums.BookingResponse;
import org.Backend.Services.CommonService;
import org.Backend.Services.EmployeeService;
import org.Backend.Services.PatientService;
import org.Backend.Utilities.BeanUtil;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ScheduleView extends SpringBeanAutowiringSupport implements Serializable {
 
    private ScheduleModel eventModel;
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private String room;
 
    private Map<Bookings, BookingDetails> bookingsMap = new HashMap<Bookings, BookingDetails>();
    
    @Autowired
    private EmployeeService es;
    
    @Autowired
    private CommonService cs;
    
    @Autowired
    private PatientService ps;
    
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        
        bookingsMap =   es.createHighlightEventsReturnBookings(eventModel);
        
        
    }
     
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
    
    
    
    
    
    public void addEvent() {

       eventModel.addEvent(event);

       FacesContext context = FacesContext.getCurrentInstance();
       DataListView dBean = context.getApplication().evaluateExpressionGet(context, "#{dataListView}", DataListView.class);       
       List<User> myUsersSelected = dBean.getMyUsersSelected();
       
       Bookings newBooking = BeanUtil.getBean(Bookings.class);       
       newBooking.setTitle(event.getTitle());
       newBooking.setStart(LocalDateTime.ofInstant(event.getStartDate().toInstant(), ZoneId.systemDefault()));
       newBooking.setEnd(LocalDateTime.ofInstant(event.getEndDate().toInstant(), ZoneId.systemDefault()));
       newBooking.setRoom(room);


       es.createBooking(myUsersSelected, newBooking);
       
       event = new DefaultScheduleEvent();
       room ="";
        
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
     
//    public void onEventMove(ScheduleEntryMoveEvent event) {
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
//         
//        addMessage(message);
//    }
//     
//    public void onEventResize(ScheduleEntryResizeEvent event) {
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
//         
//        addMessage(message);
//    }
//     
//    private void addMessage(FacesMessage message) {
//        FacesContext.getCurrentInstance().addMessage(null, message);
//    }
    
    
    
    
    
    
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public List<Map.Entry<Bookings, BookingDetails>> getBookingsMap() {
    	
    	Set<Map.Entry<Bookings, BookingDetails>> bookingsSet = bookingsMap.entrySet();
	    
	    return new ArrayList<Map.Entry<Bookings, BookingDetails>>(bookingsSet);
	}

	public void setBookingsMap(Map<Bookings, BookingDetails> bookingsMap) {
		this.bookingsMap = bookingsMap;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

 
	public void acceptBooking(Map.Entry<Bookings, BookingDetails> selectedEntry) {
		boolean result = cs.updateBookingResponse(selectedEntry.getValue().getId(), BookingResponse.ACCEPTED);
		bookingsMap =   es.createHighlightEventsReturnBookings(eventModel);
		System.out.println(result);
	}
	
	public void rejectBooking(Map.Entry<Bookings, BookingDetails> selectedEntry) {
		boolean result = cs.updateBookingResponse(selectedEntry.getValue().getId(), BookingResponse.REJECTED);
		bookingsMap =   es.createHighlightEventsReturnBookings(eventModel);
		System.out.println(result);
	}

     
	public BookingResponse getNoResp() {
	        return BookingResponse.NO_RESPONSE;
	    }

     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
    
}