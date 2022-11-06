package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
//    @Query(value = "SELECT * FROM schedule r LEFT JOIN doctor d on r.doctorId = d.id LEFT JOIN specialization s on d.specializationId = s.id", nativeQuery = true)
//    List<Schedule> findByIsShown(boolean shown);

}
