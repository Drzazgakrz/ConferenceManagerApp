package pl.krzysztof.drzazga.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class ConferenceManagerUI extends UI {

    private ConferenceSchedule conferenceSchedule;
    private UserRegistrationView userRegistrationView;
    private SignInView signInView;
    private UserLecturesView userLecturesView;
    private SetEmailView setEmailView;

    @Autowired
    public ConferenceManagerUI(ConferenceSchedule conferenceSchedule,
                               UserRegistrationView userRegistrationView,
                               SignInView signInView,
                               UserLecturesView userLecturesView,
                               SetEmailView setEmailView) {
        this.conferenceSchedule = conferenceSchedule;
        this.userRegistrationView = userRegistrationView;
        this.signInView = signInView;
        this.userLecturesView = userLecturesView;
        this.setEmailView = setEmailView;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        this.setContent(layout);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Navigator navigator = new Navigator(this, layout);
        navigator.addView("", conferenceSchedule);
        navigator.addView("register", userRegistrationView);
        navigator.addView("login", signInView);
        navigator.addView("user_lectures", userLecturesView);
        navigator.addView("set_email", setEmailView);
    }
}
