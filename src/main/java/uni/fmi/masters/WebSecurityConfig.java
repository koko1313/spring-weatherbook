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

	protected void configure(HttpSecurity http) throws Exception {
		// /** - достъп до всчки ресурси. С това позволяваме всичко на всички
		http.authorizeRequests().antMatchers("/**").permitAll()
		.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
		.and().csrf().disable();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		// когато се логнем ще минем през тук и ако user-а не отговаря на никое от тези права, той няма да получи всъщност абсолютно никакви права
		
		// hardcore-ваме двама потребители, паролата няма значение, защото после ще търсим само по username-а
		UserDetails user = User.withDefaultPasswordEncoder().username("goshko").password("password").roles("USER").build(); // user с права на USER
		UserDetails admin = User.withDefaultPasswordEncoder().username("admincho").password("password").roles("ADMIN").build(); // user с права на ADMIN
		
		return new InMemoryUserDetailsManager(user, admin);
	}
	
}
