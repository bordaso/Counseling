package org.Backend;

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
        }
		return bean;
	}

}
