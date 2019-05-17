package pl.krzysztof.drzazga.view;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import pl.krzysztof.drzazga.exception.WrongDataException;
import pl.krzysztof.drzazga.model.Lecture;
import pl.krzysztof.drzazga.model.User;
import pl.krzysztof.drzazga.service.EmailService;
import pl.krzysztof.drzazga.service.RegistrationService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@SpringComponent
@Scope(value = "prototype")
public class UserRegistrationView extends VerticalLayout implements View {

    private RegistrationService registrationService;

    private TextField username;
    private TextField email;
    private Label finalLabel;

    @Value("${email.label}")
    private String emailLabel;
    @Value("${username.label}")
    private String usernameLabel;
    @Value("${save.button}")
    private String saveButtonText;
    @Value("${registration.completed}")
    private String finalText;
    @Value("email.error")
    private String emailError;

    private Lecture lecture;

    private User user;

    @Autowired
    public UserRegistrationView(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            int conferenceId = getConferenceId(viewChangeEvent);
            this.lecture = this.registrationService.checkLecturePresence(conferenceId);
            this.removeAllComponents();
            this.initComponents();
        } catch (Exception e) {
            this.getUI().getNavigator().navigateTo("");
        }
    }

    private int getConferenceId(ViewChangeListener.ViewChangeEvent event) {
        String[] parameters = event.getParameters().split("/");
        return Integer.parseInt(parameters[0]);
    }

    private void initComponents() {
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        this.addComponent(new Label(usernameLabel));
        username = new TextField();
        this.addComponent(username);

        this.addComponent(new Label(emailLabel));
        email = new TextField();
        this.addComponent(email);

        Button saveButton = new Button(this.saveButtonText, e -> register());
        this.addComponent(saveButton);

        this.finalLabel = new Label();
        this.addComponent(finalLabel);

        this.resetUser();
    }

    private void register() {
        try {
            this.registrationService.register(this.user, this.lecture);
            this.finalLabel.setValue(finalText);
            this.resetUser();
        } catch (WrongDataException e) {
            finalLabel.setValue(e.getReason());
        } catch (ConstraintViolationException e) {
            StringBuilder message = new StringBuilder();

            for (ConstraintViolation violation : e.getConstraintViolations())
                message.append(violation.getMessage()).append(", ");

            finalLabel.setValue(message.toString());
        } catch (IOException e){
            finalLabel.setValue(this.emailError);
        }
    }

    private void resetUser() {
        this.user = new User();
        this.bindDataToFields();
    }

    private void bindDataToFields() {
        Binder binder = new Binder<>(User.class);
        binder.bindInstanceFields(this);
        binder.setBean(this.user);
    }
}
