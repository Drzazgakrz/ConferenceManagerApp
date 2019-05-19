package pl.krzysztof.drzazga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.krzysztof.drzazga.data.UsersRepository;
import pl.krzysztof.drzazga.model.Lecture;
import pl.krzysztof.drzazga.model.LecturesHasUsers;
import pl.krzysztof.drzazga.model.User;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Value("${email.header}")
    private String header;

    @Value("${email.content}")
    private String content;

    @Value("${email.date}")
    private String dateFooter;

    private UsersRepository usersRepository;

    @Autowired
    public EmailService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public void sendEmail(LecturesHasUsers lecturesHasUsers) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        Lecture lecture = lecturesHasUsers.getLecture();
        String content = this.content.replace("user", lecturesHasUsers.getUser().getUsername())
                .replace("title", lecture.getLectureName())
                .replace("date", lecture.getLectureDate().format(formatter));
        String footer = this.dateFooter.replace("date", LocalDateTime.now().format(formatter));
        String completeContent = header + "\n" + content+" "+footer;
        Files.write(Paths.get("email.txt"), completeContent.getBytes());
    }

    public void setEmail(User user) throws ConstraintViolationException {
        User currentRow = this.usersRepository.findByUsername(user.getUsername()).get();
        currentRow.setEmail(user.getEmail());
        this.usersRepository.save(currentRow);
    }
}
