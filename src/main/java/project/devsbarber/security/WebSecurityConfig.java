package project.devsbarber.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import project.devsbarber.repository.UserRepository;
import project.devsbarber.services.SSUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SSUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/h2-console/**").permitAll()
                .antMatchers("/agenda").authenticated()
                .antMatchers("/users").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/admin").access("hasAuthority('ADMIN')")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and().httpBasic();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //Usar sem banco de dados
//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("admin"))
//                .authorities("ADMIN")
//                .and()
//                .withUser("user")
//                .password(passwordEncoder().encode("password")).authorities("USER");

        //Usar com banco de dados
        auth.userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder());
    }


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler(
                            "/webjars/**",
                            "/img/**",
                            "/css/**",
                            "/js/**")
                    .addResourceLocations(
                            "classpath:/src/resources/",
                            "classpath:/static/img/",
                            "classpath:/static/css/",
                            "classpath:/static/js/");
    }
}