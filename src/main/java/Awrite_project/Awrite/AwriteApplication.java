package Awrite_project.Awrite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AwriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwriteApplication.class, args);
	}

}
