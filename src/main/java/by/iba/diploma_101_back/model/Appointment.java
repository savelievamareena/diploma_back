package by.iba.diploma_101_back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "appointment")
public class Appointment extends DataObject{

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;

    @Column(name = "beginning")
    private String beginning;

    public User getUser() {
        return user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Service getService() {
        return service;
    }

    public String getBeginning() {
        return beginning;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }
}
