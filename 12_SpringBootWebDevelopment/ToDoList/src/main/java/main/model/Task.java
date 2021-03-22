package main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(name = "create_date")
    private Date createDate;

    private String person;

    @Column(name = "complete")
    private boolean isComplete;

    public Task() {}

    public Task(int id) {
        this.id = id;
    }

    public Task(String name, String person) {
        this.name = name;
        this.person = person;
        this.createDate = new Date();
    }
}
