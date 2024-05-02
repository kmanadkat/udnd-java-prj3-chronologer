package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE :pet MEMBER OF s.pets")
    List<Schedule> findByPetsContaining(Pet pet);

    @Query("SELECT s FROM Schedule s WHERE :employee MEMBER OF s.employees")
    List<Schedule> findByEmployeesContaining(Employee employee);

    @Query("SELECT s FROM Schedule s JOIN s.pets pet WHERE pet.customer.id = :customerId")
    List<Schedule> findByCustomerContaining(Long customerId);
}
