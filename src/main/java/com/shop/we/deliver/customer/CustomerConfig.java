package com.shop.we.deliver.customer;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shop.we.deliver.customer.entity.Address;
import com.shop.we.deliver.customer.entity.Customer;
import com.shop.we.deliver.customer.repository.CustomerRepository;

@Configuration
public class CustomerConfig {
	
	  private static final Logger log = LoggerFactory.getLogger(CustomerConfig.class);

	  @Autowired
	 private  CustomerRepository postrep;
	
	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository ) {
		return args->
		{ 
			
			
			List <Address> addresses = new ArrayList<Address>();
			
			Customer michael = new Customer( "Shop we2 Deliver", "4590938464", "shabamichael@outlook.com", "07178424906",
							"Michael Shaba", "shabamichael", "Mechanic");
		Address mike1 = new Address ("Ileayo","Kola","Lagos", "Lagos", "Nigeria");
		mike1.setCustomer(michael);

		Address mike2 = new Address ("Ileayo1","Kola","Lagos", "Lagos", "Nigeria");
		mike2.setCustomer(michael);

		Address mike = new Address ("Ileayo","Kola","Lagos", "Lagos", "Nigeria");
		mike.setCustomer(michael);

		michael.getAddress().add(mike1);
		michael.getAddress().add(mike2);
		michael.getAddress().add(mike);

		
		
			Customer joy = new Customer("Shop we1 Deliver", "4590938664", "shabajoy@outlook.com", "07174724906",
					"Joy Shaba", "shabajoy", "Mechanic");
			Address joy1 = new Address ("Shamroc","Test","Midrand", "Johannesburg", "South Africa");
			joy1.setCustomer(joy);
			
			Address joy2 = new Address ("Shamroc","Test","Midrand", "Johannesburg", "South Africa");
			joy2.setCustomer(joy);

			Address joy3 = new Address ("Shamroc","Test","Midrand", "Johannesburg", "South Africa");
			joy3.setCustomer(joy);


			addresses.add(joy1);
			addresses.add(joy2);
			addresses.add(joy3);
			
			joy.getAddress().addAll(addresses);
			
			Customer blessing = new Customer("Shop we Deliver", "4598093864", "shabablessing@outlook.com", "06717424906",
					"Blessing Shaba", "shabablessing", "Mechanic");	
			Address bless = new Address ("Testing","Test","Midrand", "England", "London", blessing);
			bless.setCustomer(blessing);

			Address bless1 = new Address ("Testing","Test","Midrand", "England", "London",blessing);
			bless1.setCustomer(blessing);

			Address bless2 = new Address ("Testing","Test","Midrand", "England", "London",blessing);
			bless2.setCustomer(blessing);
			
			List <Address> bleAdd = Arrays.asList(bless1,bless2,bless);
			blessing.setAddress(bleAdd);
			
			//blessing.getAddress().addAll(List.of(bless, bless1,bless2));
			
			
			
			log.info("Preloading " + customerRepository.saveAll(List.of(michael, joy, blessing)));
			
			//this.postrep.saveAll(List.of(michael, joy, blessing));
			
};
}
}