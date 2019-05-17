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

    @Autowired
    public RegistrationService(LecturesRepository lecturesRepository,
            UsersRepository usersRepository,
            LecturesHasUsersRepository lecturesHasUsersRepository){
        this.lecturesHasUsersRepository = lecturesHasUsersRepository;
        this.usersRepository = usersRepository;
        this.lecturesRepository = lecturesRepository;

    }

    public void register(User userFromTextFields, Lecture lecture) {
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

    private void addUserToLecture(User user) throws WrongDataException {
        long count = this.lecture.getUsers().stream().filter(LecturesHasUsers::isActive).count();
        if (count == 5)
            throw new WrongDataException(this.lectureSquadComplete);

        LecturesHasUsers lecturesHasUsers = user.add(lecture);

        this.lecturesHasUsersRepository.save(lecturesHasUsers);
        this.lecturesRepository.save(lecture);
        this.usersRepository.save(user);
    }

    public Lecture checkLecturePresence(long id) {
        return this.lecturesRepository.findById(id).get();
    }
}
