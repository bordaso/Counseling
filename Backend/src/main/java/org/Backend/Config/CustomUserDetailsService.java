package org.Backend.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.Backend.DAOs.EmployeeDao;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Patient;
import org.Backend.Enums.UserType;
import org.Backend.Utilities.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private HttpServletRequest request;

	private boolean accountNonExpired = true;
	private boolean credentialsNonExpired = true;
	private boolean accountNonLocked = true;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user;

		if ((request.getParameter("empswitch") == null) || (request.getParameter("empswitch").equals("false"))) {
			user = setupPatientUser(username);
			return user;
		}

		user = setupEmployeeUser(username);
		return user;
	}

	private User setupEmployeeUser(String username) {

		EmployeeDao empDao = BeanUtil.getBean(EmployeeDao.class);
		List<Employee> emp = empDao.selectEmployeetByUsername(username);

		if (emp == null || emp.size() == 0 || emp.size() > 1) {
			throw new UsernameNotFoundException("No such user: " + username);
		}

		Employee empUser = emp.get(0);

		return new User(empUser.getUsername(), empUser.getPassword(), !empUser.getArchived(),
				accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(empUser.getType()));
	};

	private User setupPatientUser(String username) {

		PatientDao patDao = BeanUtil.getBean(PatientDao.class);
		List<Patient> pat = patDao.selectPatientByUsername(username);

		if (pat == null || pat.size() == 0 || pat.size() > 1) {
			throw new UsernameNotFoundException("No such user: " + username);
		}

		Patient patUser = pat.get(0);

		return new User(patUser.getUsername(), patUser.getPassword(), !patUser.getArchived(),
				accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(patUser.getType()));
	};

	private Collection<? extends GrantedAuthority> getAuthorities(UserType role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(role);
		return authList;
	}

	private List<GrantedAuthority> getGrantedAuthorities(UserType role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+role.toString()));
		
		if (role.equals(UserType.ADMIN)) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+UserType.EMPLOYEE.toString()));
		}

		return authorities;
	}

}
