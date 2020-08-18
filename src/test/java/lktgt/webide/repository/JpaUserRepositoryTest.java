package lktgt.webide.repository;

import lktgt.webide.domain.User;
import lktgt.webide.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaUserRepositoryTest {

    @Autowired UserService userService;
    @Autowired JpaUserRepository jpaUserRepository;

    @Test
    void save() {
        User user1 = new User();
        user1.setName("User1");

        userService.join(user1);

        User findUser = jpaUserRepository.findById(user1.getId()).get();
        assertEquals(user1.getName(),findUser.getName());
    }

    @Test
    void findById() {

    }

    @Test
    void findByName() {

    }

    @Test
    void findAll() {

    }
}