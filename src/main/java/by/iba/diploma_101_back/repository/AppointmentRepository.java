package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT * FROM appointment a JOIN schedule s on a.scheduleId = s.id WHERE s.id = ?1", nativeQuery = true)
    List<Appointment> findAllBySchedule(int scheduleId);

    //for user

    @Query(value = "SELECT * FROM appointment a JOIN schedule s on a.scheduleId = s.id WHERE a.userId = ?1 AND s.scheduleDate < ?2", nativeQuery = true)
    List<Appointment> findPastByUserId(int userId, String currentTime);

    @Query(value = "SELECT * FROM appointment a JOIN schedule s on a.scheduleId = s.id WHERE a.userId = ?1 AND s.scheduleDate >= ?2", nativeQuery = true)
    List<Appointment> findActiveByUserId(int userId, String currentTime);

    //for admin

    @Query(value = "SELECT * FROM appointment a JOIN schedule s on a.scheduleId = s.id WHERE s.scheduleDate < ?1", nativeQuery = true)
    List<Appointment> getAllPast(String today);

    @Query(value = "SELECT * FROM appointment a JOIN schedule s on a.scheduleId = s.id WHERE s.scheduleDate >= ?1", nativeQuery = true)
    List<Appointment> getAllActive(String today);
}