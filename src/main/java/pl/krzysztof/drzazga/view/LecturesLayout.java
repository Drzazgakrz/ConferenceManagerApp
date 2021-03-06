package pl.krzysztof.drzazga.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.krzysztof.drzazga.model.Lecture;
import pl.krzysztof.drzazga.model.LecturesHasUsers;

import java.util.Collection;
import java.util.List;

@SpringComponent
@Scope("prototype")
public class LecturesLayout extends VerticalLayout {

    @Autowired
    private LectureComponent lectureComponent;


    public void addHeader(String header) {
        Label label = new Label("Ścieżka " + header);
        label.setStyleName(ValoTheme.LABEL_H3);
        this.addComponent(label);
    }

    public void createLayout(List<Lecture> lectures) {
        lectures.forEach(lecture -> {
            VerticalLayout content = lectureComponent.createComponent(lecture);
            lectureComponent.addButtonToPanel(lecture, content);
            this.addComponent(content);
        });
    }

    public void createUserPanel(Collection<LecturesHasUsers> lectures) {
        this.removeAllComponents();
        lectures.forEach(lecture -> {
            VerticalLayout content = lectureComponent.createComponent(lecture.getLecture());
            lectureComponent.addCancelButton(lecture, content);
            this.addComponent(content);
        });
    }
}
