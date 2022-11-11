package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.forms.ScheduleForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.Doctor;
import by.iba.diploma_101_back.model.Schedule;
import by.iba.diploma_101_back.repository.DoctorRepository;
import by.iba.diploma_101_back.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ScheduleController {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTime = sdf.format(dt);

    @Autowired
    public ScheduleController(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/schedules")
    public List<Schedule> getSchedules() {
        return scheduleRepository.findAllActiveOrdered(currentTime);
    }

    @GetMapping("/schedules/doc/{id}")
    public List<Schedule> getSchedulesByDoctorId(@PathVariable(value = "id") int doctorId) {
        return scheduleRepository.findAllActiveByDoc(doctorId, currentTime);
    }

    @PostMapping("/schedules")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleForm scheduleForm) throws ParseException {
        ApiResponse apiResponse = new ApiResponse();

        Schedule schedule = new Schedule();

        if(scheduleForm.getId() != 0) {
            schedule = scheduleRepository.findById(scheduleForm.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", scheduleForm.getId()));
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date startParsed = formatter.parse(scheduleForm.getStartTime());
        Date endParsed = formatter.parse(scheduleForm.getEndTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startPrepared = sdf.format(startParsed);
        String endPrepared = sdf.format(endParsed);

        Optional<Doctor> doctor = doctorRepository.findById(scheduleForm.getDoctorId());
        doctor.ifPresent(schedule::setDoctor);
        schedule.setScheduleDate(scheduleForm.getScheduleDate());
        schedule.setStartTime(startPrepared);
        schedule.setEndTime(endPrepared);

        try {
            scheduleRepository.save(schedule);
        }catch (Exception e) {
            apiResponse.setMessage("Something went wrong");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable(value = "id") int id) {
        ApiResponse apiResponse = new ApiResponse();

        try{
            scheduleRepository.deleteById(id);
        }catch (Exception e) {
            apiResponse.setMessage("Error happened.");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
