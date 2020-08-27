package lktgt.webide.service;

import lktgt.webide.domain.Member;
import lktgt.webide.repository.JpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final JpaMemberRepository jpaMemberRepository;

    @Autowired
    public MemberService(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    /**
     * 회원가입
     */

    public String join(Member member){
        boolean isDup = validateDuplicateUser(member);

        if(isDup == true) return "이미 존재하는 회원입니다.";

        jpaMemberRepository.save(member);
        return "등록되었습니다.";
    }

    private boolean validateDuplicateUser(Member member){
        Optional<Member> result = jpaMemberRepository.findByName(member.getName());
        if(result.isPresent()) return true;
        else return false;
    }
}
