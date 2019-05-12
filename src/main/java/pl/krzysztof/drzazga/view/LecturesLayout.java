package pl.krzysztof.drzazga.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import pl.krzysztof.drzazga.data.LecturesRepository;
import pl.krzysztof.drzazga.model.Lecture;

import java.time.LocalDateTime;
import java.util.List;

@SpringComponent
public class LecturesLayout extends VerticalLayout {

    private List<Lecture> lectures;

    public LecturesLayout(List<Lecture> lectures) {
        this.lectures = lectures;
        lectures.forEach(lecture -> {
            LectureComponent lectureComponent = new LectureComponent(lecture);
            this.addComponent(lectureComponent);
        });
    }
}
