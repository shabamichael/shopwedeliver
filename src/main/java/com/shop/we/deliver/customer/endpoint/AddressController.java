package com.shop.we.deliver.customer.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping(path = "address/api/v1")
public class AddressController {

	
	private final static Logger LOGGER = LoggerFactory.getLogger(AddressController.class);
	private String message, standardMessage;
	
	@GetMapping(value = "/create")
	public String addressIndex() {
			return "address"; 
		}

}
