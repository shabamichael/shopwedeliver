package com.shop.we.deliver.customer;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@Controller
@RequestMapping(path = "customer/api/v1")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	private String message, standardMessage;
	
	@GetMapping(value = "/create")
	public String customerIndex() {
			return "customer"; 
		}
	
	@GetMapping(value = "/customers")
	public ResponseEntity<List<Customer>>getAllCustomers(){
		List<Customer> customers = customerService.getAllCustomers();
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK); 
	}
	
	@PostMapping("/create")
	// @RequestMapping(value = "/customer/create", method = RequestMethod.POST)
	  public String createCustomer(@RequestBody Customer customer  ,BindingResult bindingResult) {
		 LOGGER.info("Successfully added a new customer " + customer.getBusinessName()  +" addr " +  customer.getAddress().indexOf(0));

		 if (bindingResult.hasErrors()) {
			 LOGGER.error("Error on post method");
		        return "customer";
		    }
		 Customer customerInfo = customerService.addNewCustomer(customer); 
		 LOGGER.info("Successfully added a new customer " + customer.getBusinessName() + customerInfo +" addr " +  customer.getAddress().toString());
		 ResponseEntity<Customer> msg = new ResponseEntity<Customer>(customerInfo, HttpStatus.CREATED);
		 LOGGER.info(msg.toString() );
		 return "success";
	  }

	
	@GetMapping(value = "/customer/{regNo}")
	public ResponseEntity<List<Address>>findByCustomerRegno(@PathVariable("regNo") String regNo ,  Pageable pageable){
		List<Address> addresses = customerService.getAddressByCustomerRegNo(regNo);
		return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK); 
	}
	@GetMapping(value = "/customer/{email}")
	public ResponseEntity<List<Address>>findByCustomerEmail(@PathVariable("email")String email ,  Pageable pageable){
		List<Address> addresses = customerService.getAddressByCustomerEmail(email);
		return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK); 
	}
	@GetMapping(value = "/customer/{username}")
	public ResponseEntity<List<Address>>findByCustomerUsername(@PathVariable("username")String username , Pageable pageable){
		List<Address> addresses = customerService.getAddressesByCustomerUsername(username);
		return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK); 
	}
	}	 
	 
