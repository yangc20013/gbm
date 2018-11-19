package com.yang.gbm.contorllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
	
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String toLogin() {
		return "redirect:/index.html";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response,
			SecurityContextLogoutHandler handler) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		handler.setInvalidateHttpSession(true);
		handler.setClearAuthentication(true);
		handler.logout(request, response, auth);

		return "login";
	}

}
