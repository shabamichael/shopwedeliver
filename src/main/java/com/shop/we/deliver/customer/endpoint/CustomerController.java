package com.shop.we.deliver.customer.endpoint;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.we.deliver.customer.entity.Address;
import com.shop.we.deliver.customer.entity.Customer;
import com.shop.we.deliver.service.CustomerService;



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
	// @RequestMapping(value = "/create", method = RequestMethod.POST)
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

	
	@GetMapping(value = "/customer/reg/{regNo}")
	public ResponseEntity <Customer>findByCustomerRegno(@PathVariable("regNo") final String regNo ){
		Customer customer = customerService.getByCustomerRegNo(regNo);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK); 
	}
	@GetMapping(value = "/customer/email/{email}")
	public ResponseEntity<Customer>findByCustomerEmail(@PathVariable("email") final String email ){
		Customer customer = customerService.getCustomerByEmail(email);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK); 
	}
	@GetMapping(value = "/customer/username/{username}")
	public ResponseEntity<Customer>findByCustomerUsername(@PathVariable("username") final String username ){
		Customer customer = customerService.getCustomerByUsername(username);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK); 
	}
	@GetMapping(value = "/customer/id/{id}")
	public ResponseEntity<Customer>findByCustomerId(@PathVariable("id") final Long id ){
		Customer customer = customerService.getCustomerById(id);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK); 
	}
	@GetMapping(value = "/customers/domain/{domain}")
	public ResponseEntity<List<Customer>>getAllCustomersByDomain(@PathVariable ("domain") final String domain){
		List<Customer> customers = customerService.getAllCustomersByDomain(domain);
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK); 
	}
	
	@GetMapping(value = "/customer/total/count")
	public ResponseEntity<Long> customersTotalCount( ){
		Long count = customerService.getTotalCustomerCount();
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/customer/delete/{id}")
	public ResponseEntity<String>deleteCustomer(@PathVariable final Long id){
		Customer customer = customerService.deleteCustomer(id);
			return new ResponseEntity<String>(customer.getBusinessName() + " has being successfully deleted ", HttpStatus.OK);
		
	}
	@PutMapping(value = "/customer/update/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable final Long id,
											@RequestBody final Customer customer){
		Customer customer1 = customerService.setCustomerInfoById(id, customer);
		return new ResponseEntity<Customer>(customer1, HttpStatus.OK);
	}
	}	 
	 
