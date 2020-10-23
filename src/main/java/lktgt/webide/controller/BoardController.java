package lktgt.webide.controller;

import lktgt.webide.domain.Posted;
import lktgt.webide.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public String board(Model model){
        List<Posted> postedList= boardService.getList();
        model.addAttribute("postedList",postedList);
        return "board/list";
    }

    @GetMapping("/board/post")
    public String post(){
        return "board/post";
    }
}
