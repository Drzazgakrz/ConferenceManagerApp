package pl.krzysztof.drzazga.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.krzysztof.drzazga.model.Lecture;
import pl.krzysztof.drzazga.model.LecturesHasUsers;
import pl.krzysztof.drzazga.service.RegistrationService;

import java.time.format.DateTimeFormatter;

@SpringComponent
@Scope("prototype")
public class LectureComponent extends VerticalLayout {

    @Autowired
    private RegistrationService registrationService;

    public void createComponent(Lecture lecture) {
        Label lectureName = new Label(lecture.getLectureName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        Label lectureDate = new Label(lecture.getLectureDate().format(formatter));
        this.addComponent(lectureName);
        this.addComponent(lectureDate);
    }

    public void addButtonToPanel(Lecture lecture){
        long count = lecture.getUsers().stream().filter(LecturesHasUsers::isActive).count();
        if(count < 5) {
            Button registerButton = new Button("Zapisz się",
                    e -> getUI().getNavigator().navigateTo("register/" + lecture.getLectureId()));
            this.addComponent(registerButton);
        }else {
            this.addComponent(new Label("Miejsca zajęte"));
        }
    }

    public void addCancelButton(LecturesHasUsers lecturesHasUsers){
        Button cancelButton = new Button("Anuluj", e->
            this.registrationService.cancelRegistration(lecturesHasUsers)
        );
        this.addComponent(cancelButton);
    }
}
