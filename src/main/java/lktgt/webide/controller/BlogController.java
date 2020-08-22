package lktgt.webide.controller;

import lktgt.webide.domain.CodeForm;
import lktgt.webide.domain.User;
import lktgt.webide.domain.UserForm;
import lktgt.webide.repository.JpaUserRepository;
import lktgt.webide.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String create(UserForm userForm){
        User user = new User();
        log.info("id : {} , pw : {}",user.getName(),user.getPw());
        user.setName(userForm.getUserName());
        user.setPw(userForm.getUserPw());

        System.out.println(userForm.getUserName());
        System.out.println(userForm.getUserPw());

        String result = userService.join(user);
        if(result.equals("이미 존재하는 회원입니다.")) {
            return "/login/loginFail";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(UserForm userForm){
        return "pages/login";
    }

    @PostMapping("/login")
    public String login(UserForm userForm){

        Optional<User> user = jpaUserRepository.findByName(userForm.getUserName());
        if(user.isPresent()) return "redirect:/";
        return "login/loginFail";

    }

}
