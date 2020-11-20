package learn.myCookbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                // USER
                .antMatchers("/api/user/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user", "/api/user/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/user", "/api/user/*").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/user", "/api/user/*").hasAnyRole("USER", "MODERATOR")

                // REVIEW
                .antMatchers(HttpMethod.GET, "/api/review", "/api/review/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/review", "/api/review/*").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.PUT, "/api/review", "/api/review/*").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/review", "/api/review/*").hasAnyRole("USER", "MODERATOR")

                // COOKBOOK
                .antMatchers(HttpMethod.GET, "/api/cookbook", "/api/cookbook/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/cookbook", "/api/cookbook/*").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.PUT, "/api/cookbook", "/api/cookbook/*").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/cookbook", "/api/cookbook/*").hasAnyRole("USER", "MODERATOR")

                // RECIPE
                .antMatchers(HttpMethod.GET, "/api/recipe", "/api/recipe/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/recipe").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.PUT, "/api/recipe/*").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/recipe/*").hasAnyRole("USER", "MODERATOR")

                // GENERAL
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/*").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.PUT, "/*").hasAnyRole("USER", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/*").hasAnyRole("USER", "MODERATOR")

                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000/", "http://build-a-cookbook.s3.us-east-2.amazonaws.com/")
                        .allowedMethods("*");
            }
        };
    }
}
