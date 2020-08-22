package lktgt.webide.service;

import lktgt.webide.domain.Member;
import lktgt.webide.repository.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String join(Member member){
        boolean isDup = validateDuplicateUser(member);

        if(isDup == true) return "이미 존재하는 회원입니다.";

        jpaUserRepository.save(member);
        return "등록되었습니다.";
    }

    private boolean validateDuplicateUser(Member member){
        Optional<Member> result = jpaUserRepository.findByName(member.getName());
        if(result.isPresent()) return true;
        else return false;
    }
}
