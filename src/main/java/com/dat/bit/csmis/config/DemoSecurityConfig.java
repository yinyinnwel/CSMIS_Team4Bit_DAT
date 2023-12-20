package com.dat.bit.csmis.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private DataSource dataSource;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/DAT/dashboard*").hasAnyRole("ADMIN","EMPLOYEE")
			.antMatchers("/DAT/dashboard_mail_send").hasRole("ADMIN")
			.antMatchers("/DAT/lunch").hasAnyRole("ADMIN","EMPLOYEE")
			.antMatchers("/DAT/lunch_registration*").hasAnyRole("ADMIN","EMPLOYEE")
			.antMatchers("/DAT/lunch*").hasRole("ADMIN")
			.antMatchers("/DAT/restaurant*").hasRole("ADMIN")
			.antMatchers("/DAT/avoid_meat*").hasRole("ADMIN")
			.antMatchers("/DAT/staff_*").hasRole("ADMIN")
			.antMatchers("/DAT/admin_*").hasRole("ADMIN")
			.antMatchers("/DAT/upload-staff-csv").hasRole("ADMIN")
			.antMatchers("/DAT/pagination*").hasRole("ADMIN")
			.antMatchers("/DAT/holiday*").hasAnyRole("ADMIN","EMPLOYEE")
			.antMatchers("/DAT/holiday_upload-csv-file").hasRole("ADMIN")
			.antMatchers("/DAT/invoice*").hasRole("ADMIN")
			.antMatchers("/DAT/profile*").hasAnyRole("ADMIN","EMPLOYEE")
			.antMatchers("/DAT/about*").hasAnyRole("ADMIN","EMPLOYEE")
			.antMatchers("/resources/**").permitAll()
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT staff_id, password, enabled FROM staff WHERE staff_id=?")
				.authoritiesByUsernameQuery("SELECT staff_id, role FROM staff WHERE staff_id=?")
				.passwordEncoder(passwordEncoder());

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}










