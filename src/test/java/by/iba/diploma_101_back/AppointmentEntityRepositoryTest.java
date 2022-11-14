package by.iba.diploma_101_back;

import by.iba.diploma_101_back.helpers.Security;
import by.iba.diploma_101_back.model.Appointment;
import by.iba.diploma_101_back.model.Schedule;
import by.iba.diploma_101_back.model.Service;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class AppointmentEntityRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void getAppointmentByIdTest(){ // поиск по id
        Appointment appointment = appointmentRepository.findById(1).get();
        assertEquals("09:40", appointment.getBeginning());
    }

    @Test
    public void createAppointmentTest() throws NoSuchAlgorithmException {
        User user = userRepository.findById(27).get();
        Service service = serviceRepository.findById(4).get();
        Schedule schedule = scheduleRepository.findById(15).get();

        Appointment appointment = new Appointment(user, schedule, service, "12:30", null);
        assertNotNull(appointmentRepository.save(appointment));
    }
}