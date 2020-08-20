package lktgt.webide.controller;

import lktgt.webide.domain.CodeForm;
import lktgt.webide.domain.User;
import lktgt.webide.domain.UserForm;
import lktgt.webide.service.BlogService;
import lktgt.webide.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final UserService userService;

    @Autowired public BlogController(BlogService blogService, UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
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
        user.setName(userForm.getName());
        userService.join(user);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(){

        return "pages/login";
    }

}
