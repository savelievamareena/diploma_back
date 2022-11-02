package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query(value = "SELECT * FROM review r LEFT JOIN doctor d on r.doctorId = d.id LEFT JOIN specialization s on d.specializationId = s.id WHERE r.isShown = 0", nativeQuery = true)
    List<Review> findByIsShown(boolean shown);

}
