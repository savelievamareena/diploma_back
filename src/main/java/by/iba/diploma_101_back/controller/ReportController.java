package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.forms.ReportForm;
import by.iba.diploma_101_back.helpers.ApiResponse;
import by.iba.diploma_101_back.model.Appointment;
import by.iba.diploma_101_back.model.Report;
import by.iba.diploma_101_back.service.AppointmentService;
import by.iba.diploma_101_back.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReportController {
    private final ReportService reportService;
    private final AppointmentService appointmentService;

    @PostMapping("/report/{id}")
    public ResponseEntity<?> saveReport(@PathVariable(value = "id") int id, @RequestBody ReportForm reportForm) {
        ApiResponse apiResponse = new ApiResponse();
        Report report = new Report();

        if(id != 0) {
            report = reportService.findReportById(id);
        }

        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.findAppointmentById(reportForm.getAppointmentId()));
        appointment.ifPresent(report::setAppointment);

        report.setText(reportForm.getReportText());

        try {
            reportService.saveReport(report);
        }catch (Exception e) {
            apiResponse.setMessage("Ошибка, попробуйте позже");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiResponse);
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
