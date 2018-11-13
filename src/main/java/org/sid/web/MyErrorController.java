package org.sid.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class MyErrorController implements ErrorController{

	
	@RequestMapping("/error")
    public String handleError() {
        return "error";
	}
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}

}
