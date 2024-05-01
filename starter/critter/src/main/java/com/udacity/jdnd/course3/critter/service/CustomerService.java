package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Long saveCustomer(CustomerDTO customerDTO) {
        Customer customer = this.customerFromDTO(customerDTO);
        return customerRepository.save(customer).getId();
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::dtoFromCustomer).collect(Collectors.toList());
    }

    public CustomerDTO getOwnerByPetId(Long petId) {
        Optional<Customer> owner = customerRepository.findCustomerByPetId(petId);
        return owner.map(this::dtoFromCustomer).orElse(null);
    }


    private Customer customerFromDTO(CustomerDTO dto){
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setNotes(dto.getNotes());


        // List<ID> to List<Pet>
        List<Long> petIds = dto.getPetIds();
        List<Pet> pets = new ArrayList<>();

        if(!CollectionUtils.isEmpty(petIds)) {
            for (Long petId : dto.getPetIds()) {
                Pet pet = new Pet();
                pet.setId(petId);
                pets.add(pet);
            }
        }
        customer.setPets(pets);
        return customer;
    }

    private CustomerDTO dtoFromCustomer(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setNotes(customer.getNotes());
        // List<Pet> to List<ID>
        List<Long> pets = new ArrayList<>();
        for(Pet p : customer.getPets()) {
            pets.add(p.getId());
        }
        dto.setPetIds(pets);
        return dto;
    }
}
