package com.shop.we.deliver.customer;

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
	public CustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}


	//Save or Add a  new customer to the repository
	public Customer addNewCustomer(Customer customer) {
		message = "Creating a new user %s ";
		standardMessage = String.format(message, customer.getUsername());
		LOGGER.info(standardMessage);
		Optional<Customer>regNo = customerRepository.findCustomerByRegNo(customer.getRegNo());
		Optional<Customer>customerUsername = customerRepository.findCustomerByUsername(customer.getUsername());
		
		if(regNo.isPresent() ) {
			message = "registration number  %s is already in use";
			standardMessage = String.format(message, customer.getRegNo());
			LOGGER.error(standardMessage);
			throw new IllegalStateException(standardMessage);
		}
		else if(customerUsername.isPresent() ) {
			message = "username %s is not available";
			standardMessage = String.format(message, customer.getUsername());
			LOGGER.error(standardMessage);
			throw new IllegalStateException(standardMessage);
		}
		else {
			message ="New customer %s has been added at  %s";
			standardMessage = String.format(message, customer.getBusinessName(), LocalDateTime.now());
		LOGGER.info(standardMessage);
	
	
		
		 // create first address
		List<Address> addressList =  new ArrayList<Address>();
		Address address = new Address();
		
		address  = new Address (address.getStrNo(),address.getStreet(),address.getTown(), 
				address.getCity(), address.getState());
					
		LOGGER.info("The message " +  address.getStreet());
		customer.getAddress().add(address);
		
		address.setCustomer(customer);
		
	     // create second Address
		Address address1 = new Address();
		address1  = new Address (address1.getStrNo(),address1.getStreet(),address1.getTown(), 
				address1.getCity(), address1.getState());
					
		LOGGER.info("The message " +  address1.getStreet());
		customer.getAddress().add(address1);
		address1.setCustomer(customer);
		
		 // create third Address
		Address address2 = new Address (address.getStrNo(),address.getStreet(),address.getTown(), 
				address.getCity(), address.getState());
		
		
					
		LOGGER.info("The message " +  address2.getStreet());
		customer.getAddress().add(address2);
		address2.setCustomer(customer);
		
		
        // add all address into addressList. Till here we have prepared data for OneToMany
		//addressList.addAll( List.of( address, address1, address2));
		addressList.add(address);
		addressList.add(address1);
		addressList.add(address2);
		
		// Prepare data for ManyToOne
		address.setCustomer(customer);
		address1.setCustomer(customer);
		address2.setCustomer(customer);
		

		customer.setAddress(addressList);
		customer = customerRepository.save(customer);
        return customer;
       					
	/*customer = new Customer( customer.getBusinessName(), customer.getRegNo(), customer.getEmail(), 
			customer.getTelephone(),customer.getContactPersonnnel(),customer.getUsername(), 
			customer.getDomain());
		
	
		*/
		//return customerRepository.save(customer);
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
		
		//Delete a customer
		public void deleteCustomer(Long id) {
			boolean exists = customerRepository.existsById(id);
					if(!exists) {
						throw new IllegalStateException(String.format("Customer with id number:- %s  does not exist." , id));	
					}
					else {
						customerRepository.deleteById(id);
					}
		}
		
		//Get customer by business name
		public List<Customer> findCustomerByBusinessname(String businessName) {
			List <Customer> customerName = customerRepository.findAll()
					.stream().filter(name ->  name.getBusinessName()
							.equalsIgnoreCase(businessName)).sorted()
					 .collect(Collectors.toList());
			return customerName;
		}
		
		// update a customer
		@Transactional
		public void setCustomerInfoById( Long customerId, 
				String businessName, 
				String address, 
				String telephone, 
				String email, 
				String username) {
			standardMessage = String.format("Customer with id  %s does not exist", customerRepository.getOne(customerId).getId());	
			Customer customerFromDb = customerRepository.findById(customerId)
					.orElseThrow(() -> new IllegalStateException(standardMessage));
			if(businessName != null && businessName.length() > 0 && !(Objects.equals(customerFromDb.getBusinessName(),businessName))) 
			{
					customerFromDb.setBusinessName(businessName);
					message = "The Business name was updated to %s  at %s";
					standardMessage = String.format(message, businessName, LocalDateTime.now());
					LOGGER.info(standardMessage);
				    customerRepository.save(customerFromDb);
			}
			
			if(username != null && 
					username.length() > 0 &&
					!Objects.equals(customerFromDb.getUsername(), username)) 
			{
				Optional<Customer>customerOptional  = customerRepository
						.findCustomerByUsername(username);
				if(customerOptional.isPresent()) {
					throw new IllegalStateException("username taken");
				}
					customerFromDb.setUsername(username);
					message = "The username was updated to %s  at %s";
					standardMessage = String.format(message, username, LocalDateTime.now());
					LOGGER.info(standardMessage);
			}
		}

		//Get total customers in db
		public Long getTotalCustomerCount() {
			return customerRepository.count();
		}


		//Get customer by id
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).orElseThrow(()-> new IllegalStateException(String.format(USER_NOT_FOUND_MSG2, id)));
		
	}


	public List<Address> getAddressByCustomerRegNo(String regNo) {
		return customerRepository.findAddressByCustomerRegNo(regNo);
	}


	public List<Address> getAddressByCustomerEmail(String email) {
		return customerRepository.findAddressByCustomerEmail(email);
	}


	public List<Address> getAddressesByCustomerUsername(String username) {
		return customerRepository.findAddressByCustomerUsername(username);
	}



	
	
}