package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Long newScheduleId = scheduleService.saveSchedule(scheduleDTO);
        scheduleDTO.setId(newScheduleId);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedule();
        return schedules.stream().map(this::dtoFromSchedule).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleByPetId(petId);
        if(schedules != null) {
            return schedules.stream().map(this::dtoFromSchedule).collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleByEmployeeId(employeeId);
        if(schedules != null) {
            return schedules.stream().map(this::dtoFromSchedule).collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleByCustomerId(customerId);
        if(!schedules.isEmpty()) {
            return schedules.stream().map(this::dtoFromSchedule).collect(Collectors.toList());
        }
        return null;
    }

    private ScheduleDTO dtoFromSchedule (Schedule schedule){
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setDate(schedule.getDate());

        List<Long> employeeIds = schedule.getEmployees().stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
        dto.setEmployeeIds(employeeIds);

        List<Long> petIds = schedule.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
        dto.setPetIds(petIds);

        dto.setActivities(schedule.getActivities());
        return dto;
    }
}
