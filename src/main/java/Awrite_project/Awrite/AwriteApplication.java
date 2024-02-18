package Awrite_project.Awrite;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
public class AwriteApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");  // 추가
	}

	public static void main(String[] args) {
		SpringApplication.run(AwriteApplication.class, args);
	}
}
