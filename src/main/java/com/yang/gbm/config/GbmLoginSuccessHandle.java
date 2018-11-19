package com.yang.gbm.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GbmLoginSuccessHandle implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(),
				JsonEncoding.UTF8);
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("data", authentication);
			result.put("JSESSIONID", request.getSession().getId());
			
//			request.getSession().setMaxInactiveInterval(60 * 60 * 24);

			objectMapper.writeValue(jsonGenerator, result);
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}

	}

}
