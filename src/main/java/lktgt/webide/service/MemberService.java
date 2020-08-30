package lktgt.webide.service;

import lktgt.webide.config.Role;
import lktgt.webide.domain.Member;
import lktgt.webide.repository.JpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MemberService implements UserDetailsService {

    private final JpaMemberRepository jpaMemberRepository;

    @Autowired
    public MemberService(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    /**
     * 회원가입
     */

    public String join(Member member){
        Optional<Member> result = jpaMemberRepository.findByName(member.getName());

        if(result.isPresent()) return "이미 존재하는 회원입니다.";

        jpaMemberRepository.save(member);
        return "등록되었습니다.";
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Member> userWrapper = jpaMemberRepository.findByName(name);
        Member member = userWrapper.get();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(name.equals("admin@lktgt.com")){
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }
        else{
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        return new User(member.getName(),member.getPassword(),grantedAuthorities);
    }
}
