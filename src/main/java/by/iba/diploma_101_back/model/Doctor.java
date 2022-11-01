package by.iba.diploma_101_back.model;

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
@Table(name = "doctor")
public class Doctor extends DataObject{

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="education")
    private String education;

    @Column(name="bio")
    private String bio;

    @Column(name="isAvailable")
    private boolean available;

    @Column(name="fee")
    private float fee;

    @Column(name="yearsOfExperience")
    private int yearsOfExperience;

    @Column(name="category")
    private String category;

    @Column(name="profilePhotoLink")
    private String profilePhotoLink;

    @OneToOne
    @JoinColumn(name="specializationId", referencedColumnName="id")
    private Specialization specialization;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEducation() {
        return education;
    }

    public String getBio() {
        return bio;
    }

    public boolean isAvailable() {
        return available;
    }

    public float getFee() {
        return fee;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getCategory() {
        return category;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public String getProfilePhotoLink() {
        return profilePhotoLink;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setIsAvailable(boolean available) {
        this.available = available;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setProfilePhotoLink(String profilePhotoLink) {
        this.profilePhotoLink = profilePhotoLink;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
