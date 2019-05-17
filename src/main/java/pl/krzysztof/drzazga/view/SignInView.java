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
import pl.krzysztof.drzazga.service.SignInService;

import java.util.NoSuchElementException;

@SpringComponent
@Scope(value = "prototype")
public class SignInView extends VerticalLayout implements View {

    private TextField login;
    private User user;
    private SignInService signInService;
    private Label errorLabel;
    @Value("signIn.error")
    private String error;

    @Autowired
    public SignInView(User user, SignInService signInService){
        this.user = user;
        this.signInService = signInService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (user.isEmpty())
            initialize();
        else {
            this.forward();
        }
    }

    private void initialize(){
        this.addComponent(new Label("login"));

        login = new TextField();
        this.addComponent(login);

        Button submit = new Button("Zaloguj się", e->this.login());
        this.addComponent(submit);

        this.errorLabel = new Label(error);
    }

    private void login(){
        try {
            String login = this.login.getValue();
            User user = this.signInService.signIn(login);
            this.user.copy(user);
            this.forward();
        }catch (NoSuchElementException e){
            this.addComponent(errorLabel);
        }
    }

    public void forward(){
        this.getUI().getNavigator().navigateTo("user_lectures");
    }
}
