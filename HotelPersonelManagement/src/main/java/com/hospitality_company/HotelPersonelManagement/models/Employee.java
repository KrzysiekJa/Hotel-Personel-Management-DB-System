package com.hospitality_company.HotelPersonelManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private long employee_ID;
    private long position_ID;
    private String name;
    private String surname;
    private String address;
    private String sex;
    private LocalDate date_of_birth;
    private int telephone;
    private String email;
    private int number_of_vacation_days;
    private LocalDate date_of_employment;
    //private LocalDateTime last_changed;
}
