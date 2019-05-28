package org.Backend;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class DataListView extends SpringBeanAutowiringSupport implements Serializable  {
    
   private List<Car> cars1;
    
   private List<Car> cars2;
    
   private List<Car> cars3;
    
   private Car selectedCar;
   
   private String testt = "HELLLOOOO";
   
   
   
   
    
   public String getTestt() {
	return testt;
}

public void setTestt(String testt) {
	this.testt = testt;
}

@Autowired
   private CarService service;
   

public void carInTheRow(Car selectedCar) {
	   System.out.println(selectedCar.getBrand());
	
}
    
   @PostConstruct
   public void init() {
       cars1 = service.createCars(10);
       cars2 = service.createCars(5);
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
}