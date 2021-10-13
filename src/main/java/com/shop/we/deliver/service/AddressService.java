package com.shop.we.deliver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.we.deliver.customer.entity.Address;
import com.shop.we.deliver.customer.repository.AddressRepository;

@Service
public class AddressService {
	
	private final AddressRepository addressRepository;
	@Autowired
	 public AddressService(AddressRepository addressRepository) {
		super();
		this.addressRepository = addressRepository;
	}
	
	
	// create first address
	public List <Address> saveAddress(List<Address> address) {
		List<Address> addressList =  new ArrayList<Address>();
				
		addressList.addAll(address);	
		return addressRepository.saveAll(addressList);
	}

}
