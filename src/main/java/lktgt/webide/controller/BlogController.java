package lktgt.webide.controller;

import lktgt.webide.domain.CodeForm;
import lktgt.webide.domain.Member;
import lktgt.webide.domain.MemberForm;
import lktgt.webide.repository.JpaMemberRepository;
import lktgt.webide.service.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class BlogController {
    private final MemberService memberService;
    private final JpaMemberRepository jpaMemberRepository;
    private final Logger log = LogManager.getLogger(BlogController.class);
    private final PasswordEncoder passwordEncoder;

    @Autowired public BlogController(MemberService memberService, JpaMemberRepository jpaMemberRepository, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.jpaMemberRepository = jpaMemberRepository;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/member/new")
    public String createForm(){
        return "member/createMemberForm";
    }

    @PostMapping("/member/new")
    public String create(MemberForm memberForm){
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setPassword(passwordEncoder.encode(memberForm.getPassword() ) );

        String result = memberService.join(member);
        if(result.equals("이미 존재하는 회원입니다.")) {
            return "/login/loginFail";
        }
        log.info("id : {} , pw : {} register",member.getName(),member.getPassword());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "pages/login";
    }

}
