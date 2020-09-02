package lktgt.webide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BlogController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/IDE")
    public String IDE(){
        return "IDE/IDEindex";
    }


    @GetMapping("/member/new")
    public String createForm(){ return "member/createMemberForm";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login/loginindex";
    }

}
