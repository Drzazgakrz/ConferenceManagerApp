package pl.krzysztof.drzazga.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.krzysztof.drzazga.model.Lecture;
import pl.krzysztof.drzazga.model.LecturesHasUsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Value("${email.header}")
    private String header;

    @Value("${email.content}")
    private String content;

    public void sendEmail(LecturesHasUsers lecturesHasUsers) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        Lecture lecture = lecturesHasUsers.getLecture();
        String content = this.content.replace("user", lecturesHasUsers.getUser().getUsername())
                .replace("title", lecture.getLectureName())
                .replace("date", lecture.getLectureDate().format(formatter));
        String completeContent = header + "\n" + content;
        Files.write(Paths.get("email.txt"), completeContent.getBytes());
    }
}
