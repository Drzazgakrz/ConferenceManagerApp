package pl.krzysztof.drzazga.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Value;
import pl.krzysztof.drzazga.model.Lecture;
import java.time.format.DateTimeFormatter;

public class LectureComponent extends VerticalLayout {

    private Lecture lecture;

    public LectureComponent(Lecture lecture) {
        this.lecture = lecture;
        Label lectureName = new Label(this.lecture.getLectureName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        Label lectureDate = new Label(lecture.getLectureDate().format(formatter));
        Button registerButton = new Button("Zapisz się",
                e-> getUI().getNavigator().navigateTo("register/"+lecture.getLectureId()));
        this.addComponent(lectureName);
        this.addComponent(lectureDate);
        this.addComponent(registerButton);
    }
}
