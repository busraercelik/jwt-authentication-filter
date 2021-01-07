package jwt.auth.security.configs;

import jwt.auth.security.filters.LoginJWTAuthenticationFilter;
import jwt.auth.security.filters.JWTAuthorizationFilter;
import jwt.auth.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static jwt.auth.security.constants.SecurityConstant.SIGN_UP_URL;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurity(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("loading web security configuration");
        http.cors().and().csrf().disable()
                .authorizeRequests()
                //permitAll() means allow the matching url to go through without spring security blocking it
                .antMatchers(SIGN_UP_URL).permitAll()
                //any other request must be authenticated
                .anyRequest().authenticated()
                .and()
                // /login only
                .addFilter(new LoginJWTAuthenticationFilter(this.authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(this.authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //let authentication manager know which service has the "loadUserByUsername()" function impl
    //so that when there is a attempt to authenticate, this service can be used to load the user from db
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}