package com.hospitality_company.HotelPersonelManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
    private long shift_ID;
    private LocalDateTime starting_date;
    private LocalDateTime ending_date;
    private String status;
    //private LocalDateTime last_edition_date;
}
