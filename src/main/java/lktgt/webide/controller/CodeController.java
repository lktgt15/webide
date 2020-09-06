package lktgt.webide.controller;

import lktgt.webide.domain.Code;
import lktgt.webide.service.CodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CodeController {
    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code/list") // 내 Code 보기
    public String IDECodes(Principal principal, Model model){
        List<Code> codeList = codeService.getCodeList(principal.getName());
        model.addAttribute("codeList",codeList);
        return "code/list";
    }

    @GetMapping("/code")
    public String code(@RequestParam(value="id") Long id, Model model){
        model.addAttribute("code",codeService.findCodeById(id));
        return "code/view";
    }
}
