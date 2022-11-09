package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Appointment;
import by.iba.diploma_101_back.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT * FROM appointment a JOIN schedule s on a.scheduleId = s.id WHERE s.id = ?1", nativeQuery = true)
    List<Appointment> findAllBySchedule(int scheduleId);

}