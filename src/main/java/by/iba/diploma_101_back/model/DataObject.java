package by.iba.diploma_101_back.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class DataObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    public int getId() {
        return id;
    }
}
