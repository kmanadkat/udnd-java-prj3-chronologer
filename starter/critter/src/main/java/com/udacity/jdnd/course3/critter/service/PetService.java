package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Long savePet(PetDTO petDTO) {
        // Check if Owner is present
        Long ownerId = petDTO.getOwnerId();
        Optional<Customer> owner = customerRepository.findById(ownerId);
        if(owner.isPresent()) {
            Customer petOwner = owner.get();
            // Link Owner with Pet
            Pet newPet = this.petFromDto(petDTO);
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

    public PetDTO getPetById(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.map(this::dtoFromPet).orElse(null);
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream().map(this::dtoFromPet).collect(Collectors.toList());
    }

    public List<PetDTO> getPetByOwnerId(Long ownerId) {
        Optional<List<Pet>> pets = petRepository.findPetsByOwnerId(ownerId);
        return pets.map(petList -> petList.stream().map(this::dtoFromPet).collect(Collectors.toList())).orElse(null);
    }

    private Pet petFromDto(PetDTO dto) {
        Pet pet = new Pet();
        pet.setId(dto.getId());
        pet.setType(dto.getType());
        pet.setName(dto.getName());
        pet.setOwnerId(dto.getOwnerId());
        pet.setBirthDate(dto.getBirthDate());
        pet.setNotes(dto.getNotes());
        return pet;
    }

    private PetDTO dtoFromPet(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getOwnerId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }
}
