package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Long saveCustomer(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPetId(Long petId) {
        Optional<Customer> owner = customerRepository.findCustomerByPetId(petId);
        return owner.orElse(null);
    }
}
