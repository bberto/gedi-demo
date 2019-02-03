package bdi.azd.gedi.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/favicon.ico", "/h2-console/**", "/actuator/**")
        .permitAll().anyRequest().authenticated().and().formLogin().and().csrf()
        .ignoringAntMatchers("/h2-console/**").and().headers().frameOptions().sameOrigin();
  }
}
