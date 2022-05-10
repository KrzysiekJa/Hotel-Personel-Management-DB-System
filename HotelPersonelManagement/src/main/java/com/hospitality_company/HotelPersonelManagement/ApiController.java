package com.hospitality_company.HotelPersonelManagement;


import com.hospitality_company.HotelPersonelManagement.models.Employee;
import com.hospitality_company.HotelPersonelManagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    /********** Employee endpoints **********/
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        try{
            List<Employee> employeeList = employeeRepository.getAllEmployees();
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exc){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
        try{
            Employee employee = employeeRepository.getById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exc){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        try {
            Employee createdEmployee = employeeRepository.addEmployee(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        }catch (Exception exc){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") long id){
        try{
            Employee deletedEmployee = employeeRepository.deleteEmployee(id);
            return new ResponseEntity<>(deletedEmployee, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exc){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** Hotel endpoints **********/

    /********** Position endpoints **********/

    /********** Shift endpoints **********/

    /********** Skill endpoints **********/
}