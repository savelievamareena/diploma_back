package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.forms.DoctorForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.Doctor;
import by.iba.diploma_101_back.model.Specialization;
import by.iba.diploma_101_back.repository.DoctorRepository;
import by.iba.diploma_101_back.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DoctorController {
    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    @Autowired
    public DoctorController(DoctorRepository doctorRepository, SpecializationRepository specializationRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @GetMapping("/doctors/{id}")
    public Optional<Doctor> getDoctor(@PathVariable(value = "id") int id) {
        return doctorRepository.findById(id);
    }

    @GetMapping("/doctors/spec/{id}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable(value = "id") int specId) {
        return doctorRepository.findBySpecializationId(specId);
    }

    @PostMapping("/doctors/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable(value = "id") int doctorId, @RequestBody DoctorForm doctorDetails, HttpServletResponse response) {
        ApiResponse apiResponse = new ApiResponse();

        Doctor doctor = new Doctor();

        if(doctorId != 0) {
            doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId));
        }

        int specializationId = doctorDetails.getSpecializationId();

        Specialization specialization = specializationRepository.findById(specializationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Specialization", "id", specializationId));

        doctor.setFirstName(doctorDetails.getFirstName());
        doctor.setLastName(doctorDetails.getLastName());
        doctor.setBio(doctorDetails.getBio());
        doctor.setYearsOfExperience(doctorDetails.getYearsOfExperience());
        doctor.setEducation(doctorDetails.getEducation());
        doctor.setIsAvailable(doctorDetails.isAvailable());
        doctor.setFee(doctorDetails.getFee());
        doctor.setProfilePhotoLink(doctorDetails.getProfilePhotoLink());
        doctor.setCategory(doctorDetails.getCategory());
        doctor.setSpecialization(specialization);

        try {
            doctorRepository.save(doctor);
        } catch (Exception e) {
            apiResponse.setMessage("Ошибка, попробуйте позже");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable(value = "id") int id) {
        ApiResponse apiResponse = new ApiResponse();

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));

        try{
            doctorRepository.delete(doctor);
        }catch (Exception e) {
            apiResponse.setMessage("Ошибка, попробуйте позже");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
