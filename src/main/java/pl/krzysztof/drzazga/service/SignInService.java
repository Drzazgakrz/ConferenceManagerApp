package pl.krzysztof.drzazga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztof.drzazga.data.LecturesHasUsersRepository;
import pl.krzysztof.drzazga.data.UsersRepository;
import pl.krzysztof.drzazga.model.LecturesHasUsers;
import pl.krzysztof.drzazga.model.User;

import java.util.List;

@Service
public class SignInService {

    private UsersRepository usersRepository;
    private LecturesHasUsersRepository lecturesHasUsersRepository;

    private User user;

    @Autowired
    public SignInService(UsersRepository usersRepository,
                         User user,
                         LecturesHasUsersRepository lecturesHasUsersRepository){
        this.usersRepository = usersRepository;
        this.user = user;
        this.lecturesHasUsersRepository = lecturesHasUsersRepository;
    }

    public User signIn(String signIn){
        return this.usersRepository.findByUsername(signIn).get();
    }

    public void refresh(){
        User user = this.usersRepository.findByUsername(this.user.getUsername()).get();
        this.user.setLectures(this.lecturesHasUsersRepository.findByUser(user));
    }
}
