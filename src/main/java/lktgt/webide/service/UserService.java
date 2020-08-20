package lktgt.webide.service;

import lktgt.webide.domain.User;
import lktgt.webide.repository.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final JpaUserRepository jpaUserRepository;

    @Autowired
    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    /**
     * 회원가입
     */

    public String join(User user){
        boolean isDup = validateDuplicateUser(user);

        if(isDup == true) return "이미 존재하는 회원입니다.";

        jpaUserRepository.save(user);
        return "등록되었습니다.";
    }

    private boolean validateDuplicateUser(User user){
        Optional<User> result = jpaUserRepository.findByName(user.getName());
        if(result.isPresent()) return true;
        else return false;
    }
}
