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

    @ManyToMany
    @JoinTable(name = "lectures_has_users",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Size(max = 5)
    private Set<User> users;

    @NotNull
    @Column(name = "lecture_date")
    private LocalDateTime lectureDate;

    public long getLectureId() {
        return lectureId;
    }

    public void setLectureId(long lectureId) {
        this.lectureId = lectureId;
    }

    public ConferencePath getConferencePath() {
        return conferencePath;
    }

    public void setConferencePath(ConferencePath conferencePath) {
        this.conferencePath = conferencePath;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public LocalDateTime getLectureDate() {
        return lectureDate;
    }

    public void setLectureDate(LocalDateTime lectureDate) {
        this.lectureDate = lectureDate;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String columnName) {
        this.lectureName = columnName;
    }

    public Lecture() {
        this.users = new HashSet<>();
    }

    public boolean addUser(User user){
        if(this.users.size()==5)
            return false;
        this.users.add(user);
        return true;
    }
}
