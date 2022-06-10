package com.hospitality_company.HotelPersonelManagement;


import com.hospitality_company.HotelPersonelManagement.models.*;
import com.hospitality_company.HotelPersonelManagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin
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
    @Autowired
    private EmployeesSkillsRepository employeesSkillsRepository;
    @Autowired
    private  HotelsEmployeesRepository hotelsEmployeesRepository;
    @Autowired
    private  WorkPlanEmployeesRepository workPlanEmployeesRepository;


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
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc) {
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
        try{
            Employee employee = employeeRepository.getById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        try {
            Employee createdEmployee = employeeRepository.addEmployee(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        }catch (Exception exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable("id") long id){
        try{
            Boolean boolDeletedEmployee = employeeRepository.deleteEmployee(id);
            return new ResponseEntity<>(boolDeletedEmployee, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee){
        try {
            Employee updatedEmployee = employeeRepository.updateEmployee(id, employee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** Hotel endpoints **********/
    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> getAllHotels(){
        try{
            List<Hotel> hotelList = hotelRepository.getAllHotels();
            return new ResponseEntity<>(hotelList, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("id") long id){
        try{
            Hotel hotel = hotelRepository.getById(id);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/hotel")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel){
        try {
            Hotel createdHotel = hotelRepository.addHotel(hotel);
            return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
        }catch (Exception exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<Boolean> deleteHotel(@PathVariable("id") long id){
        try{
            Boolean boolDeletedHotel = hotelRepository.deleteHotel(id);
            return new ResponseEntity<>(boolDeletedHotel, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exc){
            exc.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/hotel/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable("id") long id, @RequestBody Hotel hotel){
        try {
            Hotel updatedHotel = hotelRepository.updateHotel(id, hotel);
            return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** Position endpoints **********/

    @PostMapping("/position")
    public ResponseEntity<Position> addPosition(@RequestBody Position position){
        try {
            Position createdPosition = positionRepository.addPosition(position);
            return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);
        }catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/position/{id}")
    public ResponseEntity<Boolean> deletePosition(@PathVariable("id") long id){
        try {
            Boolean deletedPosition = positionRepository.deletePosition(id);
            return new ResponseEntity<>(deletedPosition, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/position/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable("id") long id){
        try{
            Position position = positionRepository.getById(id);
            return new ResponseEntity<>(position, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/positions")
    public ResponseEntity<List<Position>> getAllPositions(){
        try{
            List<Position> positionsList = positionRepository.getAllPositions();
            return new ResponseEntity<>(positionsList, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/position/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable("id") long id, @RequestBody Position position){
        try {
            Position updatedPosition = positionRepository.updatePosition(id, position);
            return new ResponseEntity<>(updatedPosition, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** Shift endpoints **********/

    @GetMapping("/shift")
    public ResponseEntity<List<Shift>> getWorkPlan(){
        try{
            List<Shift> workplan = shiftRepository.getWorkPlan();
            return new ResponseEntity<>(workplan, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/shift")
    public ResponseEntity<Shift> addShift(@RequestBody Shift shift){
        try {
            Shift createdShift = shiftRepository.addShift(shift);
            return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
        }catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/shift/{id}")
    public ResponseEntity<Boolean> deleteShift(@PathVariable("id") long id){
        try {
            Boolean deletedShift = shiftRepository.deleteShift(id);
            return new ResponseEntity<>(deletedShift, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/shift/{id}")
    public ResponseEntity<Shift> getShiftById(@PathVariable("id") long id){
        try{
            Shift shift = shiftRepository.getById(id);
            return new ResponseEntity<>(shift, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/shift/{id}")
    public ResponseEntity<Shift> updateShift(@PathVariable("id") long id, @RequestBody Shift shift){
        try {
            Shift updatedShift = shiftRepository.updateShift(id, shift);
            return new ResponseEntity<>(updatedShift, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** Skill endpoints **********/

    @PostMapping("/skill")
    public ResponseEntity<Skill> addSkill(@RequestBody Skill skill){
        try {
            Skill createdSkill = skillRepository.addSkill(skill);
            return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/skill/{id}")
    public ResponseEntity<Boolean> deleteSkill(@PathVariable("id") long id){
        try {
            Boolean deletedSkill = skillRepository.deleteSkill(id);
            return new ResponseEntity<>(deletedSkill, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/skill/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable("id") long id){
        try{
            Skill skill = skillRepository.getById(id);
            return new ResponseEntity<>(skill, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills(){
        try{
            List<Skill> skillsList = skillRepository.getAllSkills();
            return new ResponseEntity<>(skillsList, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/skill/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable("id") long id, @RequestBody Skill skill){
        try {
            Skill updatedSkill = skillRepository.updateSkill(id, skill);
            return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** EmployeesSkill endpoints **********/

    @PostMapping("/employeesskills")
    public ResponseEntity<EmployeesSkills> addEmployeesSkills(@RequestBody EmployeesSkills employeesSkills){
        try {
            EmployeesSkills createdEmployeesSkills = employeesSkillsRepository.addEmployeesSkills(employeesSkills);
            return new ResponseEntity<>(createdEmployeesSkills, HttpStatus.CREATED);
        }catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/employeesskills/{id}")
    public ResponseEntity<Boolean> deleteEmployeesSkills(@PathVariable("id") long id){
        try{
            Boolean deletedEmployeesSkills = employeesSkillsRepository.deleteEmployeesSkills(id);
            return new ResponseEntity<>(deletedEmployeesSkills, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** HotelsEmployees endpoints **********/

    @PostMapping("/hotelsemployees")
    public ResponseEntity<HotelsEmployees> addHotelsEmployees(@RequestBody HotelsEmployees hotelsEmployees){
        try {
            HotelsEmployees createdHotelsEmployees = hotelsEmployeesRepository.addHotelsEmployees(hotelsEmployees);
            return new ResponseEntity<>(createdHotelsEmployees, HttpStatus.CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/hotelsemployees/{id}")
    public ResponseEntity<Boolean> deleteHotelsEmployees(@PathVariable("id") long id){
        try{
            Boolean deletedHotelsEmployees = hotelsEmployeesRepository.deleteHotelsEmployees(id);
            return new ResponseEntity<>(deletedHotelsEmployees, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** WorkPlanEmployees endpoints **********/

    @PostMapping("/workplanemployees")
    public ResponseEntity<WorkPlanEmployees> addWorkPlanEmployees(@RequestBody WorkPlanEmployees workPlanEmployees){
        try {
            WorkPlanEmployees createdWorkPlanEmployees = workPlanEmployeesRepository.addWorkPlanEmployees(workPlanEmployees);
            return new ResponseEntity<>(createdWorkPlanEmployees, HttpStatus.CREATED);
        }catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/workplanemployees/{id}")
    public ResponseEntity<Boolean> deleteWorkPlanEmployees(@PathVariable("id") long id){
        try{
            Boolean deletedWorkPlanEmployees = workPlanEmployeesRepository.deleteWorkPlanEmployees(id);
            return new ResponseEntity<>(deletedWorkPlanEmployees, HttpStatus.CREATED);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /********** EmployeeHotelPosition endpoints **********/

    @GetMapping("/ehp")
    public ResponseEntity<List<List>> getEmployeeHotelPosition(){
        try{
            List<List> list = hotelsEmployeesRepository.getEmployeeHotelPosition();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}