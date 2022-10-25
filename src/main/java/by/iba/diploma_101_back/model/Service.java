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
@Table(name = "service")
public class Service extends DataObject{
    @Column(name="title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "relatedSpecializationId")
    private Specialization specialization;

    @Column(name="price")
    private String price;

    @Column(name="duration")
    private String duration;

    @Column(name="description")
    private String description;

    public String getTitle() {
        return title;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public String getPrice() {
        return price;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
