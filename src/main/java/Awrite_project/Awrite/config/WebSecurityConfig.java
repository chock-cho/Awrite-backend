package Awrite_project.Awrite.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final UserDetailsService userService;

    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성: 인증/인가 및 로그인, 로그아웃 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable
                ) // csrf 비활성화
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable
                        ))
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests // 요청이 들어오면
                                .requestMatchers("/", "/login", "/signup").permitAll()
                                .anyRequest() // 이게 아닌 모든 url 요청은
                                .authenticated() // 인증되어야 함
                        )
                // 폼 로그인 기반 설정
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login") // 로그인 페이지 설정
                                .defaultSuccessUrl("/home") // 로그인 성공 시 이동할 경로 -> /home
                )
                .logout((logoutConfig) ->
                    logoutConfig
                        .logoutSuccessUrl("/login") // 로그아웃 성공 시 이동할 경로
                        .invalidateHttpSession(true) // 로그아웃 이후에 세션 전체 삭제 여부
                );
                return
                        http.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userService); // 사용자 정보를 가져올 서비스 설정
        // 이때 설정하는 서비스 클래스는 반드시 UserDetailsService를 상속받은 클래스여야 한다.
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder()); // 비밀번호 암호화를 위한 인코더 설정

        return daoAuthenticationProvider;
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 빈 등록
    }
}
