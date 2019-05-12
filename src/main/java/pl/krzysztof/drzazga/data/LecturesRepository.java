package pl.krzysztof.drzazga.data;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzysztof.drzazga.model.ConferencePath;
import pl.krzysztof.drzazga.model.Lecture;

import javax.transaction.Transactional;
import java.util.List;

public interface LecturesRepository extends JpaRepository<Lecture,Long> {
    @Transactional
    List<Lecture> getAllByConferencePath(ConferencePath path);
}
