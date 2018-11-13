package org.sid.sec;

import javax.sql.DataSource;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select email as principal , password as credentials ,active from personne where email = ? ")
		.authoritiesByUsernameQuery("select email as principal, role as role from users_roles where email = ?")
		.rolePrefix("ROLE_")
		.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	

	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
		 http
            .authorizeRequests().antMatchers("/managecoach").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
        .logout()                                    
            .permitAll();
		 http.authorizeRequests().antMatchers("/static/**").permitAll();
		 
		 http.exceptionHandling().accessDeniedPage("/403");
	    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
        .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/data/**", "/dist/**", "/img/**", "/less/**", "/scss/**", "/vendor/**")
           .antMatchers("/generated questions")
           .antMatchers("/check")
           .antMatchers("/verify")
           .antMatchers("/saveanswers")
           .antMatchers("/403")
           .antMatchers("/404")
           .antMatchers("/error")
           .antMatchers("/logout")
           .antMatchers("/changepwd")
           .antMatchers("/changepwd1")
           .antMatchers("login")
           .antMatchers("/thanks")
           .antMatchers("/changepwd")
           .antMatchers("/modifypass")
           .antMatchers("/resetpass")

           ;
    }

}
