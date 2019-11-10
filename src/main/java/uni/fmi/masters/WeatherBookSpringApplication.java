package uni.fmi.masters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class WeatherBookSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherBookSpringApplication.class, args);
	}

}
