package com.yang.gbm.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GbmLoginFailureHandle implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		String errMsg = "";
		if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
			errMsg = "账户名或者密码输入错误!";
        } else if (e instanceof LockedException) {
        	errMsg = "账户被锁定，请联系管理员!";
        } else if (e instanceof CredentialsExpiredException) {
        	errMsg = "密码过期，请联系管理员!";
        } else if (e instanceof AccountExpiredException) {
        	errMsg = "账户过期，请联系管理员!";
        } else if (e instanceof DisabledException) {
        	errMsg = "账户被禁用，请联系管理员!";
        } else {
        	errMsg = "登录失败!";
        }
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory()
				.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errMsg);
			objectMapper.writeValue(jsonGenerator, result);

		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}

	}

}
