package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT a FROM Appointment a WHERE a.schedule.id = ?1")
    List<Appointment> findAllBySchedule(int scheduleId);

    //for user
    @Query(value = "SELECT a FROM Appointment WHERE a.userId = ?1 AND a.schedule.scheduleDate < ?2 ORDER BY a.schedule.startTime")
    List<Appointment> findPastByUserId(int userId, String currentTime);

    @Query(value = "SELECT a FROM Appointment WHERE a.userId = ?1 AND a.schedule.scheduleDate >= ?2 ORDER BY a.schedule.startTime")
    List<Appointment> findActiveByUserId(int userId, String currentTime);

    //for admin
    @Query(value = "SELECT a FROM Appointment WHERE a.schedule.scheduleDate < ?1 ORDER BY a.schedule.startTime DESC")
    List<Appointment> getAllPast(String today);

    @Query(value = "SELECT a FROM Appointment WHERE a.schedule.scheduleDate >= ?1 ORDER BY a.schedule.startTime DESC")
    List<Appointment> getAllActive(String today);
}