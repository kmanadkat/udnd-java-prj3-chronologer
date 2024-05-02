package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Long savePet(Pet newPet) {
        newPet.setId(null);
        // Check if Owner is present
        Long ownerId = newPet.getOwnerId();
        Optional<Customer> owner = customerRepository.findById(ownerId);
        if(owner.isPresent()) {
            Customer petOwner = owner.get();
            // Link Owner with Pet
            newPet.setCustomer(petOwner);
            Long newPetId = petRepository.save(newPet).getId();

            // Update Owner info
            newPet.setId(newPetId);
            petOwner.getPets().add(newPet);
            customerRepository.save(petOwner);
            return newPetId;
        }
        return null;
    }

    public Pet getPetById(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.orElse(null);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetByOwnerId(Long ownerId) {
        Optional<List<Pet>> pets = petRepository.findPetsByOwnerId(ownerId);
        return pets.orElse(null);
    }
}
