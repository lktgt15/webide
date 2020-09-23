package lktgt.webide.controller;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.CodeForm;
import lktgt.webide.service.CodeService;
import lktgt.webide.service.IDEService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
public class IDEController {
    private final IDEService IDEService;
    private final CodeService codeService;

    public IDEController(IDEService IDEService, CodeService codeService) {
        this.IDEService = IDEService;
        this.codeService = codeService;
    }

    @GetMapping("/IDE")
    public String IDE(){
        return "IDE/IDEindex";
    }

    @PostMapping("/IDE") // Code를 받음
    public String IDESubmit(Principal principal, CodeForm codeForm) throws IOException {
        Code code = new Code();
        code.setCode(codeForm.getCode());
        code.setLanguage(codeForm.getLanguage());
        code.setName(principal.getName());

        if(codeForm.isRandominput()){

            Code inputCode = codeService.getCstyleCode(codeForm);
            IDEService.exec(inputCode,"RandomInputGen.cc",true,false);
            IDEService.exec(code,"Main.cc",false,true);

        }
        else{
            IDEService.exec(code,"Main.cc",false,false);
        }

        return "redirect:/";
    }
}
