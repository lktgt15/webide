package lktgt.webide.service;

//import lktgt.webide.config.Role;
//import lktgt.webide.domain.Member;
//import lktgt.webide.repository.JpaUserRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;

//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final JpaUserRepository jpaUserRepository;
//
//    public CustomUserDetailsService(JpaUserRepository jpaUserRepository) {
//        this.jpaUserRepository = jpaUserRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = jpaUserRepository.findByName(username)
//                    .orElseThrow(() -> new UsernameNotFoundException(username));
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        if(username.equals("admin")){
//            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
//        }
//        else{
//            grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
//        }
//
//    }
//}
