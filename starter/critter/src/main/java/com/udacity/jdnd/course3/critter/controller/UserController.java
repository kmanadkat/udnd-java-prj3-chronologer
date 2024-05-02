package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer newCustomer = this.customerFromDTO(customerDTO);
        Long customerId = customerService.saveCustomer(newCustomer);
        customerDTO.setId(customerId);
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream().map(this::dtoFromCustomer).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer owner = customerService.getOwnerByPetId(petId);
        if(owner != null) {
            return this.dtoFromCustomer(owner);
        }
        return null;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = this.employeeFromDto(employeeDTO);
        Long newEmployeeId = employeeService.saveEmployee(newEmployee);
        employeeDTO.setId(newEmployeeId);
        return employeeDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if(employee != null) {
            return this.dtoFromEmployee(employee);
        }
        return null;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> availableEmployees = employeeService.findEmployeeAvailability(employeeDTO.getSkills(), employeeDTO.getDate());
        List<EmployeeDTO> employeeDtos = new ArrayList<>();
        for(Employee emp: availableEmployees) {
            employeeDtos.add(this.dtoFromEmployee(emp));
        }
        return employeeDtos;
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

    private Employee employeeFromDto(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }


    private EmployeeDTO dtoFromEmployee(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());
        dto.setDaysAvailable(employee.getDaysAvailable());
        return dto;
    }
}
