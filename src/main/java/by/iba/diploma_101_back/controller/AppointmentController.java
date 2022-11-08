package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.model.Appointment;
import by.iba.diploma_101_back.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/appointments/schedule/{id}")
    public List<Appointment> getAllAppointmentsPerSchedule(@PathVariable(value = "id") int scheduleId) {
        return appointmentRepository.findAll();
    }
}
