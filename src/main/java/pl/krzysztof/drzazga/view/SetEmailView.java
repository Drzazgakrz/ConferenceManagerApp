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

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@SpringComponent
@Scope("prototype")
public class SetEmailView extends VerticalLayout implements View {

    private User user;

    TextField emailField;

    @Value("${emailChange.buttonText}")
    public String emailChangeButtonText;

    @Value("${emailChange.errorLabel}")
    public String emailChangeErrorLabel;

    private SignInService signInService;

    private EmailService emailService;


    @Autowired
    public SetEmailView(User user, SignInService signInService, EmailService emailService){
        this.user = user;
        this.signInService = signInService;
        this.emailService = emailService;
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            this.signInService.refresh();
            this.displayPanel();
        }catch (NoSuchElementException e){
            this.getUI().getNavigator().navigateTo("login");
        }
    }

    private void displayPanel() {
        this.emailField = new TextField();
        this.emailField.setValue(user.getEmail());
        this.addComponent(this.emailField);

        Button button = new Button(emailChangeButtonText, e->handleChange());
        this.addComponent(button);
    }

    private void handleChange() {
        try {
            String email = this.emailField.getValue();
            this.user.setEmail(email);
            this.emailService.setEmail(user);
        } catch (Exception e){
            this.addComponent(new Label(this.emailChangeErrorLabel));
        }
    }
}
