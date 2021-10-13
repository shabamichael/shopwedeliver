package com.shop.we.deliver.customer.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.we.deliver.customer.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
