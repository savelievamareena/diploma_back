package by.iba.diploma_101_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "report")
public class Report extends DataObject{
    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(name = "appointmentId", referencedColumnName = "id")
    @JsonIgnore
    private Appointment appointment;

    public String getText() {
        return text;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
