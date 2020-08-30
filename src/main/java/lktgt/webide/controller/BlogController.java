package lktgt.webide.controller;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.CodeForm;
import lktgt.webide.domain.Member;
import lktgt.webide.domain.MemberForm;
import lktgt.webide.repository.JpaCodeRepository;
import lktgt.webide.service.CodeService;
import lktgt.webide.service.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BlogController {
    private final MemberService memberService;
    private final CodeService codeService;
    private final Logger log = LogManager.getLogger(BlogController.class);
    private final PasswordEncoder passwordEncoder;

    @Autowired public BlogController(MemberService memberService, CodeService codeService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.codeService = codeService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home(){
        return "pages/index";
    }

    @GetMapping("/IDE")
    public String IDE(){
        return "pages/IDE";
    }

    @PostMapping("/IDE") // Code를 받음
    public String IDESubmit(Principal principal, CodeForm codeForm){
        System.out.println(codeForm.getCode());
        System.out.println(codeForm.getLanguage());
        System.out.println(principal.getName());

        Code code = new Code();
        code.setCode(codeForm.getCode());
        code.setLanguage(codeForm.getLanguage());
        code.setUsername(principal.getName());

        String result = codeService.join(code);


        return "redirect:/";
    }

    @GetMapping("/member/new")
    public String createForm(){
        return "member/createMemberForm";
    }

    @PostMapping("/member/new")
    public String create(Model model, MemberForm memberForm){
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setPassword(passwordEncoder.encode(memberForm.getPassword() ) );

        String result = memberService.join(member);
        if(result.equals("이미 존재하는 회원입니다.")) {
            model.addAttribute("error",result);
            return "member/createMemberForm";
        }
        log.info("id : {} , pw : {} register",member.getName(),member.getPassword());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "pages/login";
    }

}
