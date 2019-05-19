package pl.krzysztof.drzazga.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import pl.krzysztof.drzazga.model.User;
import pl.krzysztof.drzazga.service.EmailService;
import pl.krzysztof.drzazga.service.SignInService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@SpringComponent
@Scope("prototype")
public class SetEmailView extends VerticalLayout implements View {

    private User user;

    TextField emailField;

    @Value("${emailChange.buttonText}")
    private String emailChangeButtonText;

    @Value("${emailChange.error}")
    private String emailChangeErrorLabel;

    @Value("${emailChange.success}")
    private String successString;

    @Value("${emailChange.goBack}")
    private String goBackString;

    private SignInService signInService;

    private EmailService emailService;

    private Label finalLabel;

    @Autowired
    public SetEmailView(User user, SignInService signInService, EmailService emailService) {
        this.user = user;
        this.signInService = signInService;
        this.emailService = emailService;
        this.finalLabel = new Label();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            this.removeAllComponents();
            this.signInService.refresh();
            this.displayPanel();
        } catch (NoSuchElementException e) {
            this.getUI().getNavigator().navigateTo("login");
        }
    }

    private void displayPanel() {
        this.emailField = new TextField();
        this.emailField.setValue(user.getEmail());
        this.addComponent(this.emailField);
        this.addComponent(finalLabel);

        Button button = new Button(emailChangeButtonText, e -> handleChange());
        this.addComponent(button);
        Button navigationButton = new Button(this.goBackString, e ->
                this.getUI().getNavigator().navigateTo("user_lectures"));
        this.addComponent(navigationButton);
    }

    private void handleChange() {
        try {
            String email = this.emailField.getValue();
            this.user.setEmail(email);
            this.emailService.setEmail(user);
            this.finalLabel.setValue(successString);
        } catch (ConstraintViolationException e) {
            StringBuilder message = new StringBuilder();

            for (ConstraintViolation violation : e.getConstraintViolations())
                message.append(violation.getMessage()).append(", ");

            finalLabel.setValue(message.toString());
        } catch (Exception e) {
            this.finalLabel.setValue(this.emailChangeErrorLabel);
        }
    }
}
