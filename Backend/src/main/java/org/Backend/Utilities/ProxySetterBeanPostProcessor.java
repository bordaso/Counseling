package org.Backend.Utilities;

import org.Backend.DAOs.BookingDetailsDao;
import org.Backend.DAOs.BookingsDao;
import org.Backend.DAOs.EmployeeDao;
import org.Backend.DAOs.NotificationSetupDao;
import org.Backend.DAOs.PatientDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProxySetterBeanPostProcessor implements BeanPostProcessor {
	
	//Setup without AspectJ weaving to allow self-call from transactional methods
	
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof EmployeeDao) {
			EmployeeDao proxyBean = (EmployeeDao) bean;
			proxyBean.setMyProxy(proxyBean);
        }else 
        	if (bean instanceof PatientDao) {
        	PatientDao proxyBean = (PatientDao) bean;
			proxyBean.setMyProxy(proxyBean);
        }else 
        	if (bean instanceof BookingsDao) {
        	BookingsDao proxyBean = (BookingsDao) bean;
			proxyBean.setMyProxy(proxyBean);
        }else 
        	if (bean instanceof BookingDetailsDao) {
        	BookingDetailsDao proxyBean = (BookingDetailsDao) bean;
			proxyBean.setMyProxy(proxyBean);
        }else 
        	if (bean instanceof NotificationSetupDao) {
        	NotificationSetupDao proxyBean = (NotificationSetupDao) bean;
			proxyBean.setMyProxy(proxyBean);
        }
		
		return bean;
	}

}
