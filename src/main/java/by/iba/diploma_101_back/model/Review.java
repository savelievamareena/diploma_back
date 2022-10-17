package by.iba.diploma_101_back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "review")
public class Review extends DataObject{

    @Column(name="doctorId")
    private Integer doctorId;

    @Column(name="reviewText")
    private String reviewText;

    @Column(name="sender")
    private String sender;

    public void setDoctorId(Integer doctorId) {this.doctorId = doctorId;}

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setSender(String sender) {this.sender = sender;}

    public Integer getDoctorId() {
        return doctorId;
    }

    public String getSender() {return sender;}

    public String getReviewText() {
        return reviewText;
    }
}
