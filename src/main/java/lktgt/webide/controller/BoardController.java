package lktgt.webide.controller;

import lktgt.webide.domain.MemberForm;
import lktgt.webide.domain.Posted;
import lktgt.webide.domain.PostedForm;
import lktgt.webide.repository.BoardRepository;
import lktgt.webide.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board/list")
    public String board(Model model){
        List<Posted> postedList= boardService.getList();
        model.addAttribute("postedList",postedList);
        return "board/list";
    }

    @GetMapping("/board/post")
    public String post(){
        return "board/post";
    }

    @PostMapping("/board/post")
    public String postSubmit(Principal principal, PostedForm postedForm){
        Posted posted = new Posted();
        posted.setCategory(postedForm.getCategory());
        posted.setContents(postedForm.getContents());
        posted.setTitle(postedForm.getTitle());
        posted.setName(principal.getName());
        System.out.println(posted.getCategory());

        boardService.join(posted);

        return "redirect:/";
    }
}
