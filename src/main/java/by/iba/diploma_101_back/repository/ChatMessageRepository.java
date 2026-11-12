package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.ChatMessage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    @EntityGraph(attributePaths = {"patient", "sender"})
    List<ChatMessage> findByPatientIdOrderByIdAsc(int patientId);

    @EntityGraph(attributePaths = {"patient", "sender"})
    @Query("SELECT m FROM ChatMessage m WHERE m.id IN (SELECT MAX(m2.id) FROM ChatMessage m2 GROUP BY m2.patient.id) ORDER BY m.id DESC")
    List<ChatMessage> findLastMessagePerPatient();
}
