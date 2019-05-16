package pl.krzysztof.drzazga.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LecturesHasUsersId implements Serializable {
    private long userId;
    private long lectureId;

    public LecturesHasUsersId() {
    }

    public LecturesHasUsersId(long userId, long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getLectureId() {
        return lectureId;
    }

    public void setLectureId(long lectureId) {
        this.lectureId = lectureId;
    }
}
