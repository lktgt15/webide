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
        if(memberForm.getPw().isBlank()){
            System.out.println("공백\n");
            return "member/createMemberForm";
        }
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setPw(passwordEncoder.encode(memberForm.getPw() ) );

        String result = memberService.join(member);
        if(result.equals("이미 존재하는 회원입니다.")) {
            return "/login/loginFail";
        }
        log.info("id : {} , pw : {} register",member.getName(),member.getPw());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "pages/login";
    }

//    @PostMapping("/login")
//    public String login(MemberForm memberForm){
//        log.info("id : {} , pw : {}", memberForm.getName(), memberForm.getPw());
//        Optional<Member> user = jpaMemberRepository.findByName(memberForm.getName());
//        System.out.println(memberForm.getName());
//        if(user.isPresent()) {
//            Member member = user.get();
//            String storedPw = member.getPw();
//            String memberPw = memberForm.getPw();
//            System.out.println("멤버 이름 매칭 성공");
//            if(passwordEncoder.matches(memberPw,storedPw)) {
//                System.out.println("로그인 성공");
//                return "redirect:/";
//            }
//        }
//        System.out.println("로그인 실패");
//        return "/login/loginFail";
//    }

}
