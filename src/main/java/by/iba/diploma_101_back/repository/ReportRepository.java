package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
