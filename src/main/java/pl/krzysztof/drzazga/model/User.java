package pl.krzysztof.drzazga.model;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @NotNull
    @Size(max = 20, min = 5, message = "Niepoprawny login. Powinien mieć od 5 do 20 znaków")
    @Column(unique = true)
    private String username;

    @NotNull
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
}
