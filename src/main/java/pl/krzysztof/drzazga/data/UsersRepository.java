package pl.krzysztof.drzazga.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.krzysztof.drzazga.model.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    @Query("select user from User user left join fetch user.lectures where user.username=?1")
    Optional<User> findByUsername(String login);
}
