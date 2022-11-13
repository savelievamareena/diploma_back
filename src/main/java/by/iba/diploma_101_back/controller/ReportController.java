package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.forms.ReportForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.Appointment;
import by.iba.diploma_101_back.model.Report;
import by.iba.diploma_101_back.repository.AppointmentRepository;
import by.iba.diploma_101_back.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReportController {
    private final ReportRepository reportRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public ReportController(ReportRepository reportRepository, AppointmentRepository appointmentRepository) {
        this.reportRepository = reportRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @PostMapping("/report/{id}")
    public ResponseEntity<?> saveReport(@PathVariable(value = "id") int id, @RequestBody ReportForm reportForm, HttpServletResponse response) {
        ApiResponse apiResponse = new ApiResponse();
        Report report = new Report();

        if(id != 0) {
            report = reportRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
        }

        Optional<Appointment> appointment = appointmentRepository.findById(reportForm.getAppointmentId());
        appointment.ifPresent(report::setAppointment);

        report.setText(reportForm.getReportText());

        try {
            reportRepository.save(report);
        }catch (Exception e) {
            apiResponse.setMessage("Ошибка сохранения");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
