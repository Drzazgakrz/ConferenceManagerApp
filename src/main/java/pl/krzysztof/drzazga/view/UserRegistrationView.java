package pl.krzysztof.drzazga.view;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.krzysztof.drzazga.data.LecturesHasUsersRepository;
import pl.krzysztof.drzazga.data.LecturesRepository;
import pl.krzysztof.drzazga.data.UsersRepository;
import pl.krzysztof.drzazga.exception.WrongDataException;
import pl.krzysztof.drzazga.model.Lecture;
import pl.krzysztof.drzazga.model.LecturesHasUsers;
import pl.krzysztof.drzazga.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@SpringComponent
public class UserRegistrationView extends VerticalLayout implements View {

    private TextField username;
    private TextField email;
    private Label finalLabel;

    @Value("${email.label}")
    private String emailLabel;
    @Value("${username.label}")
    private String usernameLabel;
    @Value("${save.button}")
    private String saveButtonText;
    @Value("${username.error}")
    private String usernameError;

    @Value("${registration.error}")
    private String registrationError;

    @Value("${lectureSquad.complete}")
    private String lectureSquadComplete;

    @Value("${registration.completed}")
    private String finalText;

    private LecturesRepository lecturesRepository;
    private UsersRepository usersRepository;
    private LecturesHasUsersRepository lecturesHasUsersRepository;

    private Lecture lecture;

    private User user;


    @Autowired
    public UserRegistrationView(LecturesRepository lecturesRepository,
                                UsersRepository usersRepository,
                                LecturesHasUsersRepository lecturesHasUsersRepository) {
        this.lecturesRepository = lecturesRepository;
        this.usersRepository = usersRepository;
        this.lecturesHasUsersRepository = lecturesHasUsersRepository;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            int conferenceId = getConferenceId(viewChangeEvent);
            this.lecture = checkLecturePresence(conferenceId);
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

    private Lecture checkLecturePresence(long id) {
        return this.lecturesRepository.findById(id).get();
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

    private void bindDataToFields() {
        Binder binder = new Binder<>(User.class);
        binder.bindInstanceFields(this);
        binder.setBean(this.user);
    }

    private User getUser() throws WrongDataException {
        User user = this.usersRepository.findByUsername(this.user.getUsername());
        try {
            if (!user.equals(this.user))
                throw new WrongDataException(this.usernameError);

        } catch (NullPointerException e) {
            user = this.user;
            this.usersRepository.save(user);
        }
        return user;
    }

    private void checkPossibilityToRegister(User user) throws WrongDataException {
        LecturesHasUsers current = this.lecturesHasUsersRepository
                .findByLectureDateAndUser(user, this.lecture.getLectureDate());

        if (current != null)
            throw new WrongDataException(this.registrationError);
    }

    private void addUserToLecture(User user) throws WrongDataException {
        if (this.lecture.getUsers().size() == 5)
            throw new WrongDataException(this.lectureSquadComplete);

        LecturesHasUsers lecturesHasUsers = user.add(lecture);

        System.out.println(lecture.getUsers().size());
        this.lecturesHasUsersRepository.save(lecturesHasUsers);
        this.lecturesRepository.save(lecture);
        this.usersRepository.save(user);
    }

    private void register() {
        try {
            User user = this.getUser();
            this.checkPossibilityToRegister(user);
            this.addUserToLecture(user);
            this.finalLabel.setValue(finalText);
            this.resetUser();
        } catch (WrongDataException e) {
            finalLabel.setValue(e.getReason());
        } catch (ConstraintViolationException e){
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation violation : e.getConstraintViolations())
                message.append(violation.getMessage()).append(", ");
            finalLabel.setValue(message.toString());
        }
    }

    public void resetUser(){
        this.user = new User();
        this.bindDataToFields();
    }
}
