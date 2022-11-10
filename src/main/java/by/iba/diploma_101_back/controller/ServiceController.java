package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.model.Service;
import by.iba.diploma_101_back.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ServiceController {
    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/services")
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @GetMapping("/services/spec/{id}")
    public List<Service> getAllServicesBySpecId(@PathVariable(value = "id") int specId) {
        return serviceRepository.findAllBySpecId(specId);
    }

    @GetMapping("/services/{id}")
    public List<Service> getOffersByDepartment(@PathVariable(value = "id") int departmentId) {
        return serviceRepository.findAllByDepartmentId(departmentId);
    }
}
