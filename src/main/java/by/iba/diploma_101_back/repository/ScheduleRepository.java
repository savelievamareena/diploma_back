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
    @Query(value = "" +
            "SELECT * FROM schedule s LEFT JOIN doctor d on s.doctorId = d.id " +
            "LEFT JOIN specialization sp on d.specializationId = sp.id WHERE s.endTime >= ?1 ORDER BY s.startTime",
            nativeQuery = true)
    List<Schedule> findAllActiveOrdered(String currentTime);

    @Query(value = "SELECT * FROM schedule s LEFT JOIN doctor d on s.doctorId = d.id WHERE d.id = ?1 AND s.startTime > ?2 ORDER BY s.startTime",
            nativeQuery = true)
    List<Schedule> findAllActiveByDoc(int doctorId, String currentTime);

}
