package lktgt.webide.repository;

import lktgt.webide.domain.User;
import lktgt.webide.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaUserRepositoryTest {

    @Autowired UserService userService;
    @Autowired JpaUserRepository jpaUserRepository;

    @Test
    void 회원가입() {
        User user1 = new User();
        user1.setName("User1");

        userService.join(user1);

        User findUser = jpaUserRepository.findById(user1.getId()).get();
        assertEquals(user1.getName(),findUser.getName());
    }

    @Test
    void 중복회원예외() {
        User user1 = new User();
        user1.setName("User");

        User user2 = new User();
        user2.setName("User");

        String result = userService.join(user1);
        assertThat(result).isEqualTo("등록되었습니다.");
        String result2 = userService.join(user2);
        assertThat(result2).isEqualTo("이미 존재하는 회원입니다.");

        List<User> all = jpaUserRepository.findAll();
        for(User u: all){
            System.out.println(u.getId());
            System.out.println(u.getName());
        }
    }

}