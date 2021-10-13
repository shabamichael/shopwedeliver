package com.shop.we.deliver.service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.we.deliver.customer.entity.Address;
import com.shop.we.deliver.customer.entity.Customer;
import com.shop.we.deliver.customer.repository.AddressRepository;
import com.shop.we.deliver.customer.repository.CustomerRepository;


@Service
public class CustomerService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
	private String message, standardMessage;
	private final static  String USER_NOT_FOUND_MSG = "User with email %s not found";
	private final static  String USER_NOT_FOUND_MSG1 = "User with username %s not found";
	private final static  String USER_NOT_FOUND_MSG2 = "User with id %s not found";



	@Autowired
	private  CustomerRepository  customerRepository;
	
	@Autowired
	private AddressRepository addRepo;
	
	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}
	

	//Save or Add a  new customer to the repository
	public Customer addNewCustomer(Customer customer) {
		LOGGER.info(MessageFormat.format(
				"Creating a new customer with the following details : {0}", customer.getUsername()));
		
		Optional<Customer>regNo = customerRepository.findCustomerByRegNo(customer.getRegNo());
		Optional<Customer>uname = customerRepository.findCustomerByUsername(customer.getUsername());
		Optional<Customer>email = customerRepository.findCustomerByUsername(customer.getEmail());

		if(regNo.isPresent() ) {
			message = "registration number  %s is already in use";
			standardMessage = String.format(message, customer.getRegNo());
			LOGGER.error(standardMessage);
			throw new IllegalStateException(standardMessage);
		}
		else if(uname.isPresent() ) {
			message = "username %s is not available";
			standardMessage = String.format(message, customer.getUsername());
			LOGGER.error(standardMessage);
			throw new IllegalStateException(standardMessage);
		}
		else if (email.isPresent() ) {
			message = "email address  %s is already in use";
			standardMessage = String.format(message, customer.getEmail());
			LOGGER.error(standardMessage);
			throw new IllegalStateException(standardMessage);
		}
		else {
			message ="New customer %s is being added at  %s";
			standardMessage = String.format(message, customer.getBusinessName(), LocalDateTime.now());
		LOGGER.info(standardMessage);
	
		 // create first address
		List<Address> addressList =  new ArrayList<Address>();
		addressList.addAll(customer.getAddress());	
		
		customer = customerRepository.save(customer);
        return customer;
       					
		}		
	}		
	 
	
			
	//Get List of all customers
	public List<Customer>getAllCustomers(){
		return customerRepository.findAll(); 
	}
	
	//Get Just one customer by email
	public Customer getCustomerByEmail(String email) {
		return customerRepository.findCustomerByEmail(email)
				.orElseThrow(()-> new IllegalStateException(String .format(USER_NOT_FOUND_MSG,email)));
	}
	
	//Get Just one customer by username
		public Customer getCustomerByUsername(String username) {
			return customerRepository.findCustomerByUsername(username)
					.orElseThrow(()-> new IllegalStateException(String .format(USER_NOT_FOUND_MSG,username)));
		}
		
		//Get Just one customer by registration number
		public Customer getByCustomerRegNo(String regNo) {
			return customerRepository.findCustomerByRegNo(regNo).get();	
		}
		
		//Delete a customer
		public Customer deleteCustomer(Long id) {
			Customer customer = getCustomerById(id);
						customerRepository.deleteById(id);
						return customer;
		}

		
		//Get customer by business name
		public List<Customer> findCustomerByBusinessname(String businessName) {
			List <Customer> customerName = customerRepository.findAll()
					.stream().filter(name ->  name.getBusinessName()
							.equalsIgnoreCase(businessName)).sorted()
					 .collect(Collectors.toList());
			return customerName;
		}
		
		
		//Get total customers in db
		public Long getTotalCustomerCount() {
			return customerRepository.count();
		}

		//Get customer by id
	public Customer getCustomerById(Long id) {
		boolean exists = customerRepository.existsById(id);
		if(!exists) {
			throw new IllegalStateException(String.format("Customer with id number:- %s  does not exist." , id));	
		}
		return customerRepository.findById(id).orElseThrow(()-> new IllegalStateException(String.format(USER_NOT_FOUND_MSG2, id)));	
	}

	//Get customer by domain name
	public List<Customer> getAllCustomersByDomain(String domain) {
		return customerRepository.findCustomerByDomain(domain);
	}
		
		// update a customer
		@Transactional
		public Customer setCustomerInfoById( Long customerId,Customer customer) {
			
				String businessName = customer.getBusinessName();
				List<Address> address = customer.getAddress();
				String telephone = customer.getTelephone(); 
				String email = customer.getEmail();
				String reg = customer.getRegNo();
				String contactPerson = customer.getContactPersonnnel();
						
			standardMessage = String.format("Customer with id  %s does not exist", customerRepository.getOne(customerId).getId());	
			Customer customerFromDb = customerRepository.findById(customerId)
					.orElseThrow(() -> new IllegalStateException(standardMessage));
			if(businessName != null && businessName.length() > 0 && !(Objects.equals(customerFromDb.getBusinessName(),businessName))) 
			{
				
					customerFromDb.setBusinessName(customer.getBusinessName());
					message = "The Business name was updated to %s  at %s";
					standardMessage = String.format(message, customer.getBusinessName(), LocalDateTime.now());
					LOGGER.info(standardMessage);
				    customerRepository.save(customerFromDb);
			}
			
			if(email != null && 
					email.length() > 0 &&
					!Objects.equals(customerFromDb.getEmail(), email)) 
			{
				Optional<Customer>customerOptional  = customerRepository
						.findCustomerByEmail(email);
				if(customerOptional.isPresent()) {
					throw new IllegalStateException("email is in use");
				}
				else {
					customerFromDb.setEmail(email);
					message = "The email was updated to %s  at %s";
					standardMessage = String.format(message, email, LocalDateTime.now());
					LOGGER.info(standardMessage);
				}	
			}
			
			if(reg != null && 
					reg.length() > 0 &&
					!Objects.equals(customerFromDb.getRegNo(), email)) 
			{
				Optional<Customer>customerOptional  = customerRepository
						.findCustomerByRegNo(reg);
				if(customerOptional.isPresent()) {
					throw new IllegalStateException("Registration number is in use");
				}
				else {
					customerFromDb.setRegNo(reg);
					message = "The registation number  was updated to %s  at %s";
					standardMessage = String.format(message, reg, LocalDateTime.now());
					LOGGER.info(standardMessage);
				}
			
				customerFromDb.setTelephone(telephone);
				message = "The telephone number  was updated to %s  at %s";
				standardMessage = String.format(message, telephone, LocalDateTime.now());
				LOGGER.info(standardMessage);
				
				customerFromDb.setContactPersonnnel(contactPerson);
				message = "The Contact Person   was updated to %s  at %s";
				standardMessage = String.format(message, contactPerson, LocalDateTime.now());
				LOGGER.info(standardMessage);
			}
			
			return customer;
		}

	


	public List<Address> getAddressByCustomerEmail(String email) {
		return customerRepository.findAddressByCustomerEmail(email);
	}


	public List<Address> getAddressesByCustomerUsername(String username) {
		return customerRepository.findAddressByCustomerUsername(username);
	}


	
	
}