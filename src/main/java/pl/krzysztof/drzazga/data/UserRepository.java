package pl.krzysztof.drzazga.data;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzysztof.drzazga.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
