package lktgt.webide.controller;

import lktgt.webide.domain.Member;
import lktgt.webide.domain.MemberForm;
import lktgt.webide.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    public MemberController(PasswordEncoder passwordEncoder, MemberService memberService) {
        this.passwordEncoder = passwordEncoder;
        this.memberService = memberService;
    }

    @PostMapping("/member/new")
    public String create(Model model, MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setPassword(passwordEncoder.encode(memberForm.getPassword()));

        String result = memberService.join(member);
        model.addAttribute("result",result);

        return "redirect:/";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String exceptionHandler(Model model,Exception e){
        model.addAttribute("result",e.getMessage());
        return "member/createMemberForm";
    }
}