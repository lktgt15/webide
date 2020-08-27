package lktgt.webide.service;

import lktgt.webide.config.Role;
import lktgt.webide.domain.Member;
import lktgt.webide.repository.JpaMemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JpaMemberRepository jpaMemberRepository;

    public CustomUserDetailsService(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = jpaMemberRepository.findByName(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(username.equals("admin")){
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }
        else{
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        return new User(member.getName(),member.getPassword(),grantedAuthorities);
    }
}
