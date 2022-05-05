package com.hospitality_company.HotelPersonelManagement;


import com.hospitality_company.HotelPersonelManagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private SkillRepository skillRepository;


    @GetMapping("/test")
    public int test(){
        return 1;
    }
}