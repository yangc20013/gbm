package com.yang.gbm.contorllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/message")
public class MessageController {
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<String> list() {
		return new ResponseEntity<>(null,HttpStatus.OK);
	}

}
