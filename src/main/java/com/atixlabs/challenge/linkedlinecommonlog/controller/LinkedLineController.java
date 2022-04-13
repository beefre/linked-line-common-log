package com.atixlabs.challenge.linkedlinecommonlog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkedLineController {
	
	@GetMapping(value = "/hello")
	public String hello() {
		return "Hello World";
	}

}
