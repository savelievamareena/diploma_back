package by.iba.diploma_101_back.repository;

import by.iba.diploma_101_back.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    @Query(value = "SELECT * FROM service s WHERE s.relatedSpecializationId = ?1", nativeQuery = true)
    List<Service> findAllBySpecId(int specId);

    @Query(value = "SELECT s.*, s2.id as specializationId, s2.title as specializationTitle, d.id as departmentId, d.title as departmentTitle FROM service s JOIN specialization s2 on s.relatedSpecializationId = s2.id JOIN department d on s2.departmentId = d.id WHERE d.id = ?1 GROUP BY s.id", nativeQuery = true)
    List<Service> findAllByDepartmentId(int departmentId);
}