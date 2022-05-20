package com.hospitality_company.HotelPersonelManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlanEmployees {
    private long work_plan_Employees_ID;
    private long hotel_employee_ID;
    private long shift_ID;
}
