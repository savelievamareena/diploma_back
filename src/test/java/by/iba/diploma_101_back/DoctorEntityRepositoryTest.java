package by.iba.diploma_101_back;

import by.iba.diploma_101_back.model.Doctor;
import by.iba.diploma_101_back.model.Specialization;
import by.iba.diploma_101_back.repository.DoctorRepository;
import by.iba.diploma_101_back.repository.SpecializationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class DoctorEntityRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Test
    public void getDoctorByIdTest(){
        Doctor doctor = doctorRepository.findById(10).get();
        assertEquals("Иванович",doctor.getLastName());
    }

    @Test
    public void createDoctorTest() {
        Specialization specialization = specializationRepository.findById(3).get();

        Doctor doctor = new Doctor("Алла Казимирована", "Сорокина", "РГГУ", "Получила главную премию в конкурсе Врач Года", true,
                1.25F, 10, "первая", "https://simpledoc.ru/resume/examples/vrach/res/i/photo.jpg", specialization);

        assertNotNull(doctorRepository.save(doctor));
    }

    @Test
    public void editDoctorTest() {
        Doctor doctor = doctorRepository.findByLastName("Сорокина");
        assertEquals("РГГУ", doctor.getEducation());

        doctor.setYearsOfExperience(8);
        doctorRepository.save(doctor);

        Doctor doctorUpdated = doctorRepository.findByLastName("Сорокина");
        assertEquals(8, doctorUpdated.getYearsOfExperience());
    }
}
