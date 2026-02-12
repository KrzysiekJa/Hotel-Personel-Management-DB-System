package com.hospitality_company.HotelPersonelManagement;

import com.hospitality_company.HotelPersonelManagement.models.*;
import com.hospitality_company.HotelPersonelManagement.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private HotelRepository hotelRepository;
    @Autowired private PositionRepository positionRepository;
    @Autowired private ShiftRepository shiftRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private EmployeesSkillsRepository employeesSkillsRepository;
    @Autowired private HotelsEmployeesRepository hotelsEmployeesRepository;
    @Autowired private WorkPlanEmployeesRepository workPlanEmployeesRepository;
    @Autowired private AllDataRepository allDataRepository;

    @GetMapping("/test")
    public int test() {
        logger.info("Health check");
        return 1;
    }

    private Map<String, Object> error(String msg, HttpStatus status) {
        Map<String, Object> res = new HashMap<>();
        res.put("error", msg);
        res.put("status", status.value());
        res.put("timestamp", System.currentTimeMillis());
        return res;
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<Employee> list = employeeRepository.getAllEmployees();
            logger.info("Retrieved {} employees", list.size());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            logger.error("Error fetching employees", e);
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") long id) {
        try {
            Employee emp = employeeRepository.getById(id);
            return new ResponseEntity<>(emp, HttpStatus.OK);
        } catch (SQLException e) {
            logger.warn("Employee {} not found", id);
            return new ResponseEntity<>(error("Not found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            Employee created = employeeRepository.addEmployee(employee);
            logger.info("Created employee {}", created.getEmployee_ID());
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn("Validation: {}", e.getMessage());
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            logger.error("Error creating employee", e);
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id) {
        try {
            employeeRepository.deleteEmployee(id);
            logger.info("Deleted employee {}", id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            logger.error("Error deleting employee {}", id, e);
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        try {
            Employee updated = employeeRepository.updateEmployee(id, employee);
            logger.info("Updated employee {}", id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            logger.error("Error updating employee {}", id, e);
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hotels")
    public ResponseEntity<?> getAllHotels() {
        try {
            List<Hotel> list = hotelRepository.getAllHotels();
            logger.info("Retrieved {} hotels", list.size());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            logger.error("Error fetching hotels", e);
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable("id") long id) {
        try {
            Hotel hotel = hotelRepository.getById(id);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Not found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> addHotel(@RequestBody Hotel hotel) {
        try {
            Hotel created = hotelRepository.addHotel(hotel);
            logger.info("Created hotel {}", created.getHotel_ID());
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            logger.error("Error creating hotel", e);
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable("id") long id) {
        try {
            hotelRepository.deleteHotel(id);
            logger.info("Deleted hotel {}", id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            logger.error("Error deleting hotel", e);
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/hotel/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable("id") long id, @RequestBody Hotel hotel) {
        try {
            Hotel updated = hotelRepository.updateHotel(id, hotel);
            logger.info("Updated hotel {}", id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            logger.error("Error updating hotel", e);
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/positions")
    public ResponseEntity<?> getAllPositions() {
        try {
            List<Position> list = positionRepository.getAllPositions();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/position/{id}")
    public ResponseEntity<?> getPositionById(@PathVariable("id") long id) {
        try {
            Position pos = positionRepository.getById(id);
            return new ResponseEntity<>(pos, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Not found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/position")
    public ResponseEntity<?> addPosition(@RequestBody Position position) {
        try {
            Position created = positionRepository.addPosition(position);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/position/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable("id") long id) {
        try {
            positionRepository.deletePosition(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/position/{id}")
    public ResponseEntity<?> updatePosition(@PathVariable("id") long id, @RequestBody Position position) {
        try {
            Position updated = positionRepository.updatePosition(id, position);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/shifts")
    public ResponseEntity<?> getWorkPlan() {
        try {
            List<Shift> list = shiftRepository.getWorkPlan();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/shift/{id}")
    public ResponseEntity<?> getShiftById(@PathVariable("id") long id) {
        try {
            Shift shift = shiftRepository.getById(id);
            return new ResponseEntity<>(shift, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Not found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/shift")
    public ResponseEntity<?> addShift(@RequestBody Shift shift) {
        try {
            Shift created = shiftRepository.addShift(shift);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/shift/{id}")
    public ResponseEntity<?> deleteShift(@PathVariable("id") long id) {
        try {
            shiftRepository.deleteShift(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/shift/{id}")
    public ResponseEntity<?> updateShift(@PathVariable("id") long id, @RequestBody Shift shift) {
        try {
            Shift updated = shiftRepository.updateShift(id, shift);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/skills")
    public ResponseEntity<?> getAllSkills() {
        try {
            List<Skill> list = skillRepository.getAllSkills();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/skill/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable("id") long id) {
        try {
            Skill skill = skillRepository.getById(id);
            return new ResponseEntity<>(skill, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Not found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/skill")
    public ResponseEntity<?> addSkill(@RequestBody Skill skill) {
        try {
            Skill created = skillRepository.addSkill(skill);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/skill/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable("id") long id) {
        try {
            skillRepository.deleteSkill(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/skill/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable("id") long id, @RequestBody Skill skill) {
        try {
            Skill updated = skillRepository.updateSkill(id, skill);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/employeesskills")
    public ResponseEntity<?> addEmployeesSkills(@RequestBody EmployeesSkills employeesSkills) {
        try {
            EmployeesSkills created = employeesSkillsRepository.addEmployeesSkills(employeesSkills);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employeesskills/{id}")
    public ResponseEntity<?> deleteEmployeesSkills(@PathVariable("id") long id) {
        try {
            employeesSkillsRepository.deleteEmployeesSkills(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employeesskillshotels")
    public ResponseEntity<?> getEmployeeSkillsHotels() {
        try {
            List<List<String>> list = employeesSkillsRepository.getEmployeeHotelSkills();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/hotelsemployees")
    public ResponseEntity<?> addHotelsEmployees(@RequestBody HotelsEmployees hotelsEmployees) {
        try {
            HotelsEmployees created = hotelsEmployeesRepository.addHotelsEmployees(hotelsEmployees);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/hotelsemployees/{id}")
    public ResponseEntity<?> deleteHotelsEmployees(@PathVariable("id") long id) {
        try {
            hotelsEmployeesRepository.deleteHotelsEmployees(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hotelsemployeespositions")
    public ResponseEntity<?> getEmployeeHotelPosition() {
        try {
            List<List<String>> list = hotelsEmployeesRepository.getEmployeeHotelPosition();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/workplanemployee")
    public ResponseEntity<?> addWorkPlanEmployees(@RequestBody WorkPlanEmployees workPlanEmployees) {
        try {
            WorkPlanEmployees created = workPlanEmployeesRepository.addWorkPlanEmployees(workPlanEmployees);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(error(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/workplanemployee/{id}")
    public ResponseEntity<?> deleteWorkPlanEmployees(@PathVariable("id") long id) {
        try {
            workPlanEmployeesRepository.deleteWorkPlanEmployees(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/workplanemployees")
    public ResponseEntity<?> getWorkPlanEmployees() {
        try {
            List<List<?>> list = workPlanEmployeesRepository.getWorkPlanEmployees();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/alldata")
    public ResponseEntity<?> getAllData() {
        try {
            List<AllData> list = allDataRepository.getAllData();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(error("Database error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}