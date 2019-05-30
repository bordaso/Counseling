package org.Backend;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class FilterView extends SpringBeanAutowiringSupport implements Serializable {
    
   private List<Car> cars;
    
   private List<Car> filteredCars;
    
   @Autowired
   private CarService service;

   @PostConstruct
   public void init() {
       cars = service.createCars(10);
   }
    
   public boolean filterByPrice(Object value, Object filter, Locale locale) {
       String filterText = (filter == null) ? null : filter.toString().trim();
       if(filterText == null||filterText.equals("")) {
           return true;
       }
        
       if(value == null) {
           return false;
       }
        
       return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
   }
    
   public List<String> getBrands() {
       return service.getBrands();
   }
    
   public List<String> getColors() {
       return service.getColors();
   }
    
   public List<Car> getCars() {
       return cars;
   }

   public List<Car> getFilteredCars() {
       return filteredCars;
   }

   public void setFilteredCars(List<Car> filteredCars) {
       this.filteredCars = filteredCars;
   }

   public void setService(CarService service) {
       this.service = service;
   }
}
