package lktgt.webide.repository;

import lktgt.webide.domain.Member;
import lktgt.webide.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaMemberRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired
    JpaMemberRepository jpaMemberRepository;

    @Test
    void 회원가입() {
        Member member1 = new Member();
        member1.setName("User1");

        memberService.join(member1);

        Member findMember = jpaMemberRepository.findById(member1.getId()).get();
        assertEquals(member1.getName(), findMember.getName());
    }

    @Test
    void 중복회원예외() {
        Member member1 = new Member();
        member1.setName("User");

        Member member2 = new Member();
        member2.setName("User");

        String result = memberService.join(member1);
        assertThat(result).isEqualTo("등록되었습니다.");
        String result2 = memberService.join(member2);
        assertThat(result2).isEqualTo("이미 존재하는 회원입니다.");

        List<Member> all = jpaMemberRepository.findAll();
        for(Member u: all){
            System.out.println(u.getId());
            System.out.println(u.getName());
        }
    }

}