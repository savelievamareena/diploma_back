package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query(value = "SELECT * FROM doctor d WHERE d.specializationId = ?1 AND d.isAvailable = 1", nativeQuery = true)
    List<Doctor> findBySpecializationId(int specializationId);

    List<Doctor> findByLastName(String s);
}
