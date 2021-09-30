package com.shop.we.deliver.customer;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	/*
	
	
	
	@Query("SELECT c FROM Customer c WHERE c.businessName = ?*")
	public abstract Optional<Customer>findCustomerByBusinessname(String businessName);
	
	@Transactional
	@Modifying
	@Query("update Customer u set u.businessName = ?1, u.regNo = ?2 where u.id = ?")
	void setCustomerInfoById(String firstname, String lastname, Integer userId);
	
	*/

	@Query("SELECT b FROM Customer b WHERE b.regNo = ?1")
	public abstract Optional<Customer> findCustomerByRegNo(String regNo);
	
	@Query("SELECT c FROM Customer c WHERE c.username = ?1")
	public abstract Optional<Customer> findCustomerByUsername(String username);
	
	@Query("SELECT c FROM Customer c WHERE c.email = ?1")
	public abstract Optional <Customer> findCustomerByEmail(String email);
	
	//@Query(nativeQuery = true, value = "SELECT * FROM Customer  WHERE Customer.regNo = :regNo    DESC LIMIT 20")
	@Query("SELECT s FROM Customer s ORDER BY s.regNo DESC")
	public abstract List<Address> findAddressByCustomerRegNo(String regNo);

	
	//@Query(nativeQuery = true, value = "SELECT * FROM Customer  WHERE Customer.regNo = :regNo   DESC LIMIT 20")
	@Query("SELECT s FROM Customer s ORDER BY s.email DESC")
	public abstract List<Address> findAddressByCustomerEmail(String email);

	//@Query(nativeQuery = true, value = "SELECT * FROM Customer  WHERE Customer.regNo = :regNo  DESC LIMIT 20")
	@Query("SELECT s FROM Customer s ORDER BY s.username DESC")
	public abstract List<Address>findAddressByCustomerUsername(String username);

}
