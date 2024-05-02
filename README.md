# Critter Chronologer

Critter Chronologer is a Software as a Service application that provides a scheduling interface for a small business that takes care of animals. This Spring Boot project will allow users to create pets, owners, and employees, and then schedule events for employees to provide services for pets.

## Project Overview

The Critter Chronologer project is part of the Java Web Developer Nanodegree program at Udacity. It has following features:

- Data Persistence Using Spring JPA & MySQL
- Repository Pattern in Data Layer
- Service & Controller Layer

# Architecture

```mermaid
classDiagram
    class Customer {
        -Long id
        -String name
        -String phoneNumber
        -String notes
        -List~Pet~ pets
    }

    class Pet {
        -Long id
        -PetType type
        -String name
        -long ownerId
        -LocalDate birthDate
        -String notes
        -Customer customer
    }

    class Employee {
        -Long id
        -String name
        -Set~EmployeeSkill~ skills
        -Set~DayOfWeek~ daysAvailable
    }

    class Schedule {
        -Long id
        -List~Employee~ employees
        -List~Pet~ pets
        -LocalDate date
        -Set~EmployeeSkill~ activities
    }

    Customer "1" *-- "*" Pet : owns
    Schedule "*" -- "*" Employee : involves
    Schedule "*" -- "*" Pet : involves


```

## Project Rubric

For detailed information about the project requirements and rubric, please refer to the [project rubric](https://learn.udacity.com/rubric/2787).

## Prerequisites

- Java JDK v17 or above
- MySQL server running on Port 3306 & Database `critter` exists

## Installation

To run the application locally, follow these steps:

1. Clone the repository: `git clone https://github.com/kmanadkat/udnd-java-prj3-chronologer`
2. Navigate to the project directory: `cd udnd-java-prj3-chronologer`
3. Build the project: `./mvnw package`
4. Run the application: `./mvnw spring-boot:run`
5. Open your web browser and visit: `http://localhost:8080`

## Technologies Used

- Java
- Spring Boot
- MySQL
- Spring JPA

## Submission

#### Check Availability

![](./submission/01 Check Availability.png)

#### Save Schedule

![](./submission/02 Save Schedule.png)

#### Find Schedule By Owner

![](./submission/03 Find Schedule By Owner.png)

#### Find Schedule By Pet

![](./submission/04 Find Schedule By Pet.png)

#### Find Schedule By Employee

![](./submission/05 Find Schedule By Employee.png)

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
