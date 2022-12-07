package by.iba.diploma_101_back.service;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.model.Report;
import by.iba.diploma_101_back.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public Report findReportById(int id) {
        return reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Report", "id", id));
    }
}
