package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.model.Specialization;
import by.iba.diploma_101_back.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SpecializationController {
    private final SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationController(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    @GetMapping("/specializations")
    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }
}
