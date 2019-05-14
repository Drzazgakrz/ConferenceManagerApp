package pl.krzysztof.drzazga.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.krzysztof.drzazga.model.ConferencePath;
import pl.krzysztof.drzazga.model.Lecture;

import javax.transaction.Transactional;
import java.util.List;

public interface LecturesRepository extends JpaRepository<Lecture,Long> {
    @Transactional
    @Query("select lecture from Lecture lecture left join fetch lecture.users where lecture.conferencePath=?1")
    List<Lecture> getAllByConferencePath(ConferencePath path);
}
