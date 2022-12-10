package zerobase.board.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import zerobase.board.board.model.BoardInput;
import zerobase.board.board.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String boardIndex() {
        return "board/index";
    }

    @GetMapping("/board/register")
    public String register(){
        return "board/register";
    }

    @PostMapping("/board/register")
    public String registerSubmit(Model model,
                                 BoardInput parameter){

        boolean result = boardService.register(parameter);
        model.addAttribute("result", result);

        return "redirect:/board/register";
    }
}
