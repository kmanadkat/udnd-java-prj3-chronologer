package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Long saveSchedule(ScheduleDTO scheduleDTO) {
        Schedule newSchedule = new Schedule();
        List<Pet> pets = petRepository.findAllById(scheduleDTO.getPetIds());
        List<Employee> employees = employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
        newSchedule.setDate(scheduleDTO.getDate());
        newSchedule.setEmployees(employees);
        newSchedule.setPets(pets);
        newSchedule.setActivities(scheduleDTO.getActivities());
        return scheduleRepository.save(newSchedule).getId();
    }

    public List<Schedule> getAllSchedule() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPetId(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.map(value -> scheduleRepository.findByPetsContaining(value)).orElse(null);
    }

    public List<Schedule> getScheduleByEmployeeId(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.map(value -> scheduleRepository.findByEmployeesContaining(value)).orElse(null);
    }

    public List<Schedule> getScheduleByCustomerId(Long customerId) {
        return scheduleRepository.findByCustomerContaining(customerId);
    }
}
