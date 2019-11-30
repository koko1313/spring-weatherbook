package uni.fmi.masters;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private ApplicationUserDetailService userDetailService;
	
	public WebSecurityConfig(ApplicationUserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		// /** - достъп до всчки ресурси. С това позволяваме всичко на всички
		http.authorizeRequests().antMatchers("/**").permitAll()
		.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
		.and().csrf().disable();
		http.headers().frameOptions().disable(); // без това h2 показва blank екран ????? wtf
	}
	
	protected void configure(ApplicationUserDetailService userDetailService) throws Exception {
		/*
		http.authorizeRequests().antMatchers("/**").permitAll()
		.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
		.and().csrf().disable();
		*/
		
		//super.configure();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		return userDetailService;
	}
	
}
