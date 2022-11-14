package by.iba.diploma_101_back;

import by.iba.diploma_101_back.model.Doctor;
import by.iba.diploma_101_back.model.Schedule;
import by.iba.diploma_101_back.repository.DoctorRepository;
import by.iba.diploma_101_back.repository.ScheduleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ScheduleEntityRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void getScheduleByIdTest() {
        Schedule schedule = scheduleRepository.findById(10).get();
        assertEquals("2022-11-08", schedule.getScheduleDate());
    }

    @Test
    public void createScheduleTest() {
        Doctor doctor = doctorRepository.findById(10).get();

        Schedule schedule = new Schedule(doctor, "2022-12-24", "2022-12-24 09:30:00", "2022-12-24 13:30:00");
        assertNotNull(scheduleRepository.save(schedule));
    }





}
