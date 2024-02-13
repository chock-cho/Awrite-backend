package Awrite_project.Awrite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.parameters.P;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("christer10@ewhain.net");
        mailSender.setPassword("lxwkhrkjrqnzchdd");

        Properties properties = new Properties();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }
}
