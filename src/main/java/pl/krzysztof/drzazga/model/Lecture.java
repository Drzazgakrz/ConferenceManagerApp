package pl.krzysztof.drzazga.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lectures")
public class Lecture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private long lectureId;

    @Column(name = "conference_path")
    private ConferencePath conferencePath;

    @NotNull
    @Column(name = "lecture_name")
    private String lectureName;

    @OneToMany(mappedBy = "lecture")
    @Size(max = 5)
    private Set<LecturesHasUsers> users;

    @NotNull
    @Column(name = "lecture_date")
    private LocalDateTime lectureDate;

    public long getLectureId() {
        return lectureId;
    }


    public Set<LecturesHasUsers> getUsers() {
        return users;
    }

    public LocalDateTime getLectureDate() {
        return lectureDate;
    }


    public String getLectureName() {
        return lectureName;
    }

    public Lecture() {
        this.users = new HashSet<>();
    }
}
