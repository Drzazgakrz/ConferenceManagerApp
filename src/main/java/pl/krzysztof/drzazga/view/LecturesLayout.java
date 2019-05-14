package pl.krzysztof.drzazga.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Value;
import pl.krzysztof.drzazga.data.LecturesRepository;
import pl.krzysztof.drzazga.model.Lecture;

import java.time.LocalDateTime;
import java.util.List;

@SpringComponent
public class LecturesLayout extends VerticalLayout {

    private List<Lecture> lectures;

    public void addHeader(String header){
        Label label = new Label("Ścieżka "+header);
        label.setStyleName(ValoTheme.LABEL_H3);
        this.addComponent(label);
    }

    public void createLayout(List<Lecture> lectures){
        this.lectures = lectures;
        lectures.forEach(lecture -> {
            LectureComponent lectureComponent = new LectureComponent(lecture);
            this.addComponent(lectureComponent);
        });
    }
}
