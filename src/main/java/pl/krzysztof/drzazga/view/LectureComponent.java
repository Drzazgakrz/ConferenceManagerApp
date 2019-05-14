package pl.krzysztof.drzazga.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import pl.krzysztof.drzazga.model.Lecture;
import java.time.format.DateTimeFormatter;

public class LectureComponent extends VerticalLayout {

    private Lecture lecture;

    public LectureComponent(Lecture lecture) {
        this.lecture = lecture;
        Label lectureName = new Label(this.lecture.getLectureName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        Label lectureDate = new Label(lecture.getLectureDate().format(formatter));
        this.addComponent(lectureName);
        this.addComponent(lectureDate);
        if(lecture.getUsers().size() < 5) {
            Button registerButton = new Button("Zapisz się",
                    e -> getUI().getNavigator().navigateTo("register/" + lecture.getLectureId()));
            this.addComponent(registerButton);
        }else {
            this.addComponent(new Label("Miejsca zajęte"));
        }
    }
}
