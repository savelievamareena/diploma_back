package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.model.Service;
import by.iba.diploma_101_back.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Service> getAllSpecializations() {
        return serviceRepository.findAll();
    }
}
