package by.iba.diploma_101_back.forms;

public class DoctorForm {
    private int id;
    private String firstName;
    private String lastName;
    private String bio;
    private int yearsOfExperience;
    private String education;
    private boolean available;
    private float fee;
    private String profilePhotoLink;
    private String category;
    private int specializationId;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBio() {
        return bio;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getEducation() {
        return education;
    }

    public boolean isAvailable() {
        return available;
    }

    public float getFee() {
        return fee;
    }

    public String getProfilePhotoLink() {
        return profilePhotoLink;
    }

    public String getCategory() {
        return category;
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setYearsOfExperience(int earsOfExperience) {
        this.yearsOfExperience = earsOfExperience;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setIsAvailable(boolean available) {
        this.available = available;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public void setProfilePhotoLink(String profilePhotoLink) {
        this.profilePhotoLink = profilePhotoLink;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }
}
