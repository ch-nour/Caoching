package org.sid.sec;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {
	
	@RequestMapping(value="/login")
	public String login()
	{
		return "/login";
	}
	
	@RequestMapping("/403")
	private String acessDenied() {
		return "403"; 
	}
	
	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
	public String logoutDo(HttpServletRequest request,HttpServletResponse response){
	HttpSession session= request.getSession(false);
	    SecurityContextHolder.clearContext();
	         session= request.getSession(false);
	        if(session != null) {
	            session.invalidate();
	        }
	        for(Cookie cookie : request.getCookies()) {
	            cookie.setMaxAge(0);
	        }
	    return "redirect:/login?logout";
	}

}
