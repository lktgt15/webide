package lktgt.webide.controller;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.CodeForm;
import lktgt.webide.domain.RandomInputForm;
import lktgt.webide.service.CodeService;
import lktgt.webide.service.IDEService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class IDEController {
    private final IDEService IDEService;

    public IDEController(IDEService IDEService) {
        this.IDEService = IDEService;
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

        IDEService.exec(code);

        return "redirect:/";
    }
}
