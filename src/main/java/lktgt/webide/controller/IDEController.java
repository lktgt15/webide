package lktgt.webide.controller;

import lktgt.webide.domain.Code;
import lktgt.webide.domain.CodeForm;
import lktgt.webide.service.CodeService;
import lktgt.webide.service.IDEService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class IDEController {
    private final IDEService IDEService;
    private final CodeService codeService;

    public IDEController(IDEService IDEService, CodeService codeService) {
        this.IDEService = IDEService;
        this.codeService = codeService;
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

    @GetMapping("/IDE/codes") // 내 Code 보기
    public String IDECodes(Principal principal, Model model){
        List<Code> codeList = codeService.getCodeList(principal.getName());
        model.addAttribute("codeList",codeList);
        return "/IDE/codes";
    }
}
