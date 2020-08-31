package lktgt.webide.controller;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.CodeForm;
import lktgt.webide.service.IDEService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class IDEController {
    private final IDEService IDEService;

    public IDEController(IDEService IDEService) {
        this.IDEService = IDEService;
    }

    @PostMapping("/IDE") // Code를 받음
    public String IDESubmit(Principal principal, CodeForm codeForm){
        Code code = new Code();
        code.setCode(codeForm.getCode());
        code.setLanguage(codeForm.getLanguage());
        code.setUsername(principal.getName());

        IDEService.join(code);
        String result = IDEService.exec(code);

        return "redirect:/";
    }
}
