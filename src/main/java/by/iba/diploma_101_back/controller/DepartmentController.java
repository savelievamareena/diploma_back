package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.DTO.OfferItemDTO;
import by.iba.diploma_101_back.model.Department;
import by.iba.diploma_101_back.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/offers")
    public List<OfferItemDTO> getOffers() {

        System.out.println(jdbcTemplate);
        String query = "SELECT service.title as serviceTitle, service.price as price, d.title as departmentTitle FROM service " +
                "LEFT JOIN specialization s on service.relatedSpecializationId = s.id " +
                "LEFT JOIN department d on s.departmentId = d.id LIMIT 12";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(OfferItemDTO.class));
    }
}
