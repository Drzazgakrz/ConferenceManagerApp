package pl.krzysztof.drzazga.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.krzysztof.drzazga.data.LecturesRepository;
import pl.krzysztof.drzazga.model.Lecture;

@SpringComponent
public class UserRegistrationView extends VerticalLayout implements View {

    private TextField username;
    private TextField email;
    private String emailLabel;
    private String usernameLabel;
    private String saveButtonText;
    private LecturesRepository lecturesRepository;
    private Lecture lecture;

    @Autowired
    public UserRegistrationView(LecturesRepository lecturesRepository,
                                @Value("${username.label}") String usernameLabel,
                                @Value("${email.label}") String emailLabel,
                                @Value("${save.button}") String saveButtonText) {
        this.lecturesRepository = lecturesRepository;
        this.usernameLabel = usernameLabel;
        this.emailLabel = emailLabel;
        this.saveButtonText = saveButtonText;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        int conferenceId = getConferenceId(viewChangeEvent);
        this.lecture = checkLecturePresence(conferenceId);
        if (this.lecture == null) {
            this.getUI().getNavigator().navigateTo("");
        }
        if (this.email == null || this.username == null)
            this.initComponents();
    }

    private int getConferenceId(ViewChangeListener.ViewChangeEvent event) {
        try {
            String[] parameters = event.getParameters().split("/");
            return Integer.parseInt(parameters[0]);
        } catch (Exception e) {
            return 0;
        }
    }

    private Lecture checkLecturePresence(int id) {
        return this.lecturesRepository.findAll().stream()
                .filter(lecture -> lecture.getLectureId() == id).findFirst().orElse(null);
    }

    private void initComponents() {
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.removeAllComponents();
        username = new TextField();
        email = new TextField();
        this.addComponent(new Label(usernameLabel));
        this.addComponent(username);
        this.addComponent(new Label(emailLabel));
        this.addComponent(email);
        Button saveButton = new Button(this.saveButtonText, e -> register());
        this.addComponent(saveButton);
    }

    private void register() {

    }
}
