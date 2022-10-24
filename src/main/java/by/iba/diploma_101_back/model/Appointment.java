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
    private Schedule scheduleId;

    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service serviceId;

    @Column(name = "beginning")
    private String beginning;

    public User getUser() {
        return user;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public Service getServiceId() {
        return serviceId;
    }

    public String getBeginning() {
        return beginning;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setServiceId(Service serviceId) {
        this.serviceId = serviceId;
    }

    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }
}
