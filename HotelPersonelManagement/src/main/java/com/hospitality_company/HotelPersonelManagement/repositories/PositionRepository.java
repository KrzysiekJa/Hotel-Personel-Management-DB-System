package com.hospitality_company.HotelPersonelManagement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PositionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


}
