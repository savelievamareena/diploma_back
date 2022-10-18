package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByUserId(int userId);
}
