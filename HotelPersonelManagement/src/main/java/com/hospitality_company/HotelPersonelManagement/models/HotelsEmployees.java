package com.hospitality_company.HotelPersonelManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelsEmployees {
    private long hotel_employee_ID;
    private long hotel_ID;
    private long employee_ID;
}
