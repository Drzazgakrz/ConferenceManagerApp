package pl.krzysztof.drzazga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.krzysztof.drzazga.data.LecturesHasUsersRepository;
import pl.krzysztof.drzazga.data.LecturesRepository;
import pl.krzysztof.drzazga.data.UsersRepository;
import pl.krzysztof.drzazga.exception.WrongDataException;
import pl.krzysztof.drzazga.model.Lecture;
import pl.krzysztof.drzazga.model.LecturesHasUsers;
import pl.krzysztof.drzazga.model.User;

import java.io.IOException;

@Service
public class RegistrationService {

    private LecturesRepository lecturesRepository;
    private UsersRepository usersRepository;
    private LecturesHasUsersRepository lecturesHasUsersRepository;

    private Lecture lecture;
    private User user;

    @Value("${username.error}")
    private String usernameError;

    @Value("${registration.error}")
    private String registrationError;

    @Value("${lectureSquad.complete}")
    private String lectureSquadComplete;

    private EmailService emailService;

    @Autowired
    public RegistrationService(LecturesRepository lecturesRepository,
                               UsersRepository usersRepository,
                               LecturesHasUsersRepository lecturesHasUsersRepository, EmailService emailService) {
        this.lecturesHasUsersRepository = lecturesHasUsersRepository;
        this.usersRepository = usersRepository;
        this.lecturesRepository = lecturesRepository;
        this.emailService = emailService;
    }

    public void register(User userFromTextFields, Lecture lecture) throws IOException {
        this.lecture = lecture;
        this.user = userFromTextFields;
        User user = this.getUser();
        this.checkPossibilityToRegister(user);
        this.addUserToLecture(user);
    }

    private User getUser() throws WrongDataException {
        User user;
        try {
            user = this.usersRepository.findByUsername(this.user.getUsername()).get();
            if (!user.equals(this.user))
                throw new WrongDataException(this.usernameError);

        } catch (Exception e) {
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

    private void addUserToLecture(User user) throws WrongDataException, IOException {
        long count = this.lecture.getUsers().stream().filter(LecturesHasUsers::isActive).count();
        if (count == 5)
            throw new WrongDataException(this.lectureSquadComplete);

        LecturesHasUsers lecturesHasUsers = user.add(lecture);

        this.lecturesHasUsersRepository.save(lecturesHasUsers);
        this.lecturesRepository.save(lecture);
        this.usersRepository.save(user);
        this.emailService.sendEmail(lecturesHasUsers);
    }

    public Lecture checkLecturePresence(long id) {
        return this.lecturesRepository.findById(id).get();
    }

    public void cancelRegistration(LecturesHasUsers lecturesHasUsers) {
        lecturesHasUsers.setActive(false);
        this.lecturesHasUsersRepository.save(lecturesHasUsers);
    }
}
