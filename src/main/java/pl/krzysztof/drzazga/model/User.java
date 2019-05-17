package pl.krzysztof.drzazga.model;

import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@SpringComponent
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @NotNull(message = "Podaj login")
    @Size(max = 20, min = 5, message = "Niepoprawny login. Powinien mieć od 5 do 20 znaków")
    @Column(unique = true)
    private String username;

    @NotNull(message = "Podaj email")
    @Column(unique = true)
    @Email(message = "Niepoprawny email")
    @Pattern(regexp = ".+@.+\\..+", message = "Niepoprawny email")
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<LecturesHasUsers> lectures;

    public User() {
        this.lectures = new HashSet<>();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<LecturesHasUsers> getLectures() {
        return lectures;
    }

    public void setLectures(Set<LecturesHasUsers> lectures) {
        this.lectures = lectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    public LecturesHasUsers add(Lecture lecture){
        LecturesHasUsers lecturesHasUsers = new LecturesHasUsers(this,lecture);
        this.lectures.add(lecturesHasUsers);
        lecture.getUsers().add(lecturesHasUsers);
        return lecturesHasUsers;
    }

    public boolean isEmpty(){
        return this.email==null || this.username == null;
    }

    public void copy(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.userId = user.getUserId();
        this.lectures = user.getLectures();
    }
}
