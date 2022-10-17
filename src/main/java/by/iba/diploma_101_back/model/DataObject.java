package by.iba.diploma_101_back.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class DataObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name="createdAt")
    protected String createdAt;

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
