package com.shop.we.deliver.customer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;




	
	/**
	 * @author Michael Shaba
	 *
	 */
	@Entity
	@Table(
			name = "customer",
			uniqueConstraints = {
			@UniqueConstraint(name="regNo_unique", columnNames="regNo")
	})


	public class Customer implements Serializable  {
		

		@Id
		@SequenceGenerator(
				name = "customer_sequence",
				initialValue = 1,
				sequenceName = "customer_sequence",
				allocationSize = 1
				)
		@GeneratedValue(
				strategy = GenerationType.AUTO,
				generator = "customer_sequence")
		@Column(name="Id")
		private Long addId;
		
		@Column(name = "businessName", columnDefinition = "TEXT" )
		private  String businessName;
		
		
		@Column(name = "regNo", nullable = false,  unique =true,columnDefinition = "TEXT" )
		private  String regNo;
		
		
		@Column(name = "email", nullable = false, unique =true,columnDefinition = "TEXT" )
		private  String email;
		
		
		@Column(name = "telephone", nullable = false,columnDefinition = "TEXT" )
		private  String telephone;
		
		
		@Column(name = "contactPersonnel",nullable = false, columnDefinition = "TEXT" )
		private  String contactPersonnnel;
		
			
		@Column(name = "username", nullable = false, unique =true,columnDefinition = "TEXT" )
		private  String username;
		
		
		@Column(name = "domain", nullable = false, columnDefinition = "TEXT" )
		private  String domain;
			
		@JsonManagedReference
		@OneToMany(
			  mappedBy = "customer",  
			  fetch = FetchType.LAZY, 
			  cascade = CascadeType.ALL, 
			  orphanRemoval = true)
		private List <Address> address = new ArrayList<Address>();



		public Long getId() {
			return addId;
		}



		public void setId(Long id) {
			this.addId = id;
		}



		public String getBusinessName() {
			return businessName;
		}



		public void setBusinessName(String businessName) {
			this.businessName = businessName;
		}



		public String getRegNo() {
			return regNo;
		}



		public void setRegNo(String regNo) {
			this.regNo = regNo;
		}



		public String getEmail() {
			return email;
		}



		public void setEmail(String email) {
			this.email = email;
		}



		public String getTelephone() {
			return telephone;
		}



		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}



		public String getContactPersonnnel() {
			return contactPersonnnel;
		}



		public void setContactPersonnnel(String contactPersonnnel) {
			this.contactPersonnnel = contactPersonnnel;
		}



		public String getUsername() {
			return username;
		}



		public void setUsername(String username) {
			this.username = username;
		}



		public String getDomain() {
			return domain;
		}



		public void setDomain(String domain) {
			this.domain = domain;
		}



		public List<Address> getAddress() {
			return address;
		}



		public void setAddress(List<Address> address) {
			this.address = address;
		}



		public Customer() {
			super();
			// TODO Auto-generated constructor stub
		}



		public Customer(String businessName, String regNo, String email, String telephone, String contactPersonnnel,
				String username, String domain) {
			super();
			this.businessName = businessName;
			this.regNo = regNo;
			this.email = email;
			this.telephone = telephone;
			this.contactPersonnnel = contactPersonnnel;
			this.username = username;
			this.domain = domain;
		}
		
		


}
