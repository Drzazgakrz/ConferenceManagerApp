package pl.krzysztof.drzazga.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "lectures_has_users")
@Entity
public class LecturesHasUsers {

    @EmbeddedId
    private LecturesHasUsersId lecturesHasUsersId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("lectureId")
    private Lecture lecture;

    public LecturesHasUsers(User user, Lecture lecture) {
        this.user = user;
        this.lecture = lecture;
        this.lecturesHasUsersId = new LecturesHasUsersId(user.getUserId(), lecture.getLectureId());
    }

    public LecturesHasUsers() {
    }

    public LecturesHasUsersId getLecturesHasUsersId() {
        return lecturesHasUsersId;
    }

    public void setLecturesHasUsersId(LecturesHasUsersId lecturesHasUsersId) {
        this.lecturesHasUsersId = lecturesHasUsersId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
