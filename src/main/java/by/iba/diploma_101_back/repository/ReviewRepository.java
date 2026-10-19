package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @EntityGraph(attributePaths = {"doctor", "doctor.specialization"})
    List<Review> findByShown(boolean shown);

    @Query(value = "SELECT r FROM Review r WHERE r.shown = ?1 AND r.doctor.id = ?2")
    List<Review> findShownByDoctor(boolean shown, int doctorId);
}