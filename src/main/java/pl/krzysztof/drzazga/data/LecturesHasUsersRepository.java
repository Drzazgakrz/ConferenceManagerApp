package pl.krzysztof.drzazga.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krzysztof.drzazga.model.LecturesHasUsers;
import pl.krzysztof.drzazga.model.LecturesHasUsersId;
import pl.krzysztof.drzazga.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface LecturesHasUsersRepository extends JpaRepository<LecturesHasUsers, LecturesHasUsersId> {
    @Transactional
    @Query("select lecturesHasUsers from LecturesHasUsers lecturesHasUsers where lecturesHasUsers.user=:user and lecturesHasUsers.lecture.lectureDate=:date")
    LecturesHasUsers findByLectureDateAndUser(@Param("user")User user, @Param("date")LocalDateTime dateTime);
}
