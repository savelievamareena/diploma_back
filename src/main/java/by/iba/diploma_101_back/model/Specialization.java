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
@Table(name = "specialization")
public class Specialization extends DataObject{
    @Column(name="title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

    public String getTitle() {
        return title;
    }

    public Department getDepartment() {
        return department;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
