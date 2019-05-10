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

    @ManyToMany
    @JoinTable(name = "lectures_has_users",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Size(max = 5)
    private Set<User> users;

    @NotNull
    @Column(name = "order_in_path")
    private int orderInPath;

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

    public int getOrderInPath() {
        return orderInPath;
    }

    public void setOrderInPath(int orderInPath) {
        this.orderInPath = orderInPath;
    }

    public Lecture() {
        this.users = new HashSet<>();
    }
}
