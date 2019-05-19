package pl.krzysztof.drzazga.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import pl.krzysztof.drzazga.model.LecturesHasUsers;
import pl.krzysztof.drzazga.model.User;
import pl.krzysztof.drzazga.service.SignInService;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@SpringComponent
@Scope(value = "prototype")
public class UserLecturesView extends VerticalLayout implements View {

    private User user;

    private LecturesLayout lecturesLayout;

    private SignInService signInService;

    @Value("${emailChange.navigation}")
    private String navigationButtonText;
    @Value("${mainPage.header}")
    private String mainPageButtonText;

    @Autowired
    public UserLecturesView(User user, LecturesLayout lecturesLayout, SignInService signInService){
        this.user = user;
        this.lecturesLayout = lecturesLayout;
        this.signInService = signInService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            this.removeAllComponents();
            this.signInService.refresh();
            this.displayPanel();
        }catch (NoSuchElementException e){
            this.getUI().getNavigator().navigateTo("login");
        }
    }

    private void displayPanel(){
        this.lecturesLayout.createUserPanel(this.user.getLectures().stream()
                .filter(LecturesHasUsers::isActive).collect(Collectors.toList()));
        this.addComponent(lecturesLayout);
        Button setEmailNavigationButton = new Button(this.navigationButtonText, e->
                this.getUI().getNavigator().navigateTo("set_email"));
        this.addComponent(setEmailNavigationButton);
        Button mainPageNavigationButton = new Button(this.mainPageButtonText, e->
                this.getUI().getNavigator().navigateTo(""));
        this.addComponent(mainPageNavigationButton);
    }
}
