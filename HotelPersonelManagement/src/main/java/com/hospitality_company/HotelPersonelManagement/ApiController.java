package com.hospitality_company.HotelPersonelManagement;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/test")
    public int test(){
        return 1;
    }
}