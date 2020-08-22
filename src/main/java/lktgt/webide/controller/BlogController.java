package lktgt.webide.controller;

import lktgt.webide.domain.CodeForm;
import lktgt.webide.domain.Member;
import lktgt.webide.domain.MemberForm;
import lktgt.webide.repository.JpaUserRepository;
import lktgt.webide.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class BlogController {
    private final UserService userService;
    private final JpaUserRepository jpaUserRepository;
    private final Logger log = LogManager.getLogger(BlogController.class);

    @Autowired public BlogController(UserService userService, JpaUserRepository jpaUserRepository) {
        this.userService = userService;
        this.jpaUserRepository = jpaUserRepository;
    }

    @GetMapping("/")
    public String home(){
        return "pages/index";
    }

    @GetMapping("/IDE")
    public String webIDE(){
        return "pages/webIDE";
    }

    @PostMapping("/IDE") // Code를 받음
    public String IDESubmit(CodeForm codeForm){
        System.out.println(codeForm.getCode());
        System.out.println(codeForm.getLanguage());

        return "redirect:/";
    }

    @GetMapping("/user/new")
    public String createForm(){
        return "user/createUserForm";
    }

    @PostMapping("/user/new")
    public String create(MemberForm memberForm){
        Member member = new Member();
        member.setName(memberForm.getUserName());
//        member.setPw(passwordEncoder.encode(memberForm.getUserPw() ) );

        System.out.println(memberForm.getUserName());
        System.out.println(memberForm.getUserPw());

        String result = userService.join(member);
        if(result.equals("이미 존재하는 회원입니다.")) {
            return "/login/loginFail";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(MemberForm memberForm){
        return "pages/login";
    }

    @PostMapping("/login")
    public String login(MemberForm memberForm){

        log.info("id : {} , pw : {}", memberForm.getUserName(), memberForm.getUserPw());
        Optional<Member> user = jpaUserRepository.findByName(memberForm.getUserName());
        if(user.isPresent()) return "redirect:/";
        return "login/loginFail";

    }

}
