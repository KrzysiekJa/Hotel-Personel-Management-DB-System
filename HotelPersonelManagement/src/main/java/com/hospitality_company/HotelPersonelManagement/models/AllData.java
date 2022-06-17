package com.hospitality_company.HotelPersonelManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllData {
    private long work_plan_Employees_ID;
    private String employee_name;
    private String employee_surname;
    private long employee_ID;
    private String hotel_name;
    private long hotel_ID;
    private long shift_ID;
    private LocalDateTime starting_date;
    private LocalDateTime ending_date;


}
