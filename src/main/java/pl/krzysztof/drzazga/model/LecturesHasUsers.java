package pl.krzysztof.drzazga.model;

import javax.persistence.*;

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

    private boolean isActive;

    public LecturesHasUsers(User user, Lecture lecture) {
        this.user = user;
        this.lecture = lecture;
        this.lecturesHasUsersId = new LecturesHasUsersId(user.getUserId(), lecture.getLectureId());
        this.isActive=true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
