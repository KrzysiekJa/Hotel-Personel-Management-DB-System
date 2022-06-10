package com.hospitality_company.HotelPersonelManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    private long hotel_ID;
    private String name;
    private String address;
    private int telephone;
    private String email;
    private String standard;
    private int rooms_number;
    private Date creation_date;
    //private LocalDateTime last_changed;
}
