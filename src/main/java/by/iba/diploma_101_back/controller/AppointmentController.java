package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.forms.AppointmentForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.Appointment;
import by.iba.diploma_101_back.model.Schedule;
import by.iba.diploma_101_back.model.Service;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.AppointmentRepository;
import by.iba.diploma_101_back.repository.ScheduleRepository;
import by.iba.diploma_101_back.repository.ServiceRepository;
import by.iba.diploma_101_back.repository.UserRepository;
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
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository, UserRepository userRepository, ScheduleRepository scheduleRepository, ServiceRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/appointments/schedule/{id}")
    public List<Appointment> getAllAppointmentsPerSchedule(@PathVariable(value = "id") int scheduleId) {
        return appointmentRepository.findAllBySchedule(scheduleId);
    }

    @PostMapping("/appointments")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentForm appointmentForm, HttpServletResponse response) {
        ApiResponse apiResponse = new ApiResponse();
        Appointment appointment = new Appointment();

        Optional<User> user = userRepository.findById(appointmentForm.getUserId());
        user.ifPresent(appointment::setUser);

        Optional<Schedule> schedule = scheduleRepository.findById(appointmentForm.getScheduleId());
        schedule.ifPresent(appointment::setSchedule);

        Optional<Service> service = serviceRepository.findById(appointmentForm.getServiceId());
        service.ifPresent(appointment::setService);

        appointment.setBeginning(appointmentForm.getBeginning());

        try {
            appointmentRepository.save(appointment);
        } catch (Exception e) {
            apiResponse.setMessage("Something went wrong");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
