package com.bolsadeideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.bolsadeideas.springboot.app.models.service.JpaUserDetailService;

@Configuration
public class SpringSecurityConfig{
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JpaUserDetailService userDetailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
				.antMatchers("/ver/**").hasAnyRole("USER")
				.antMatchers("/uploads/**").hasAnyRole("USER")
				.antMatchers("/form/**").hasAnyRole("ADMIN")
				.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				.antMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login")
				.permitAll()
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403");
 
		return http.build();
	}


	
//	@Bean
//	public UserDetailsService userDetailsService() throws Exception {
//		
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("felipe").password(passwordEncoder().encode("user_pass")).roles("USER").build());
//		manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin_pass")).roles("ADMIN", "USER").build());
// 
//		return manager;
//	}
	
	@Autowired
	public void ConfigureGlobal(AuthenticationManagerBuilder build) throws Exception{
		
		build.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncoder);
		
//		build.jdbcAuthentication()
//		.dataSource(dataSource)
//		.passwordEncoder(passwordEncoder)
//		.usersByUsernameQuery("select username, password, enabled from usuarios where username=?")
//		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join usuarios u on (a.user_id=u.id) where u.username=?");
	}
}
