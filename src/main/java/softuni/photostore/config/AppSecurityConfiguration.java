package softuni.photostore.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import softuni.photostore.model.entity.enums.UserRoleEnum;
import softuni.photostore.web.handlers.LoginSuccHandler;
import softuni.photostore.web.handlers.LogoutSuccHandler;

@Configuration
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final LogoutSuccHandler logoutSuccHandler;
    private final LoginSuccHandler loginSuccHandler;

    public AppSecurityConfiguration(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder, LogoutSuccHandler logoutSuccHandler, LoginSuccHandler loginSuccHandler) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.logoutSuccHandler = logoutSuccHandler;
        this.loginSuccHandler = loginSuccHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/", "/users/login", "/users/register").permitAll()
                .antMatchers("/manage/users").hasRole(UserRoleEnum.ADMIN.name())
                .antMatchers("/manage/user/**").authenticated()
                .antMatchers("/**/manage/**").hasRole(UserRoleEnum.ADMIN.name())
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .successHandler(loginSuccHandler)
                .failureForwardUrl("/users/login-error")
                .and()
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessHandler(logoutSuccHandler)
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService).
                passwordEncoder(passwordEncoder);
    }
}
