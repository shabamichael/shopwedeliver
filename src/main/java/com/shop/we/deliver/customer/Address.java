package com.shop.we.deliver.customer;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;


@Entity
public class Address  implements Serializable {

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String strNo;
	private String street;
	private String town;
	private String city;
	private String state;
	
    @ManyToOne(fetch = FetchType.LAZY , cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_customer_id" , referencedColumnName = "id")
	private Customer customer;
	
	

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(String strNo, String street, String town, String city, String state) {
		super();
		this.strNo = strNo;
		this.street = street;
		this.town = town;
		this.city = city;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	
	
}
