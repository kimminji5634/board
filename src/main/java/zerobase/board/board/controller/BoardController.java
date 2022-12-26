package zerobase.board.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zerobase.board.board.entity.Board;
import zerobase.board.board.model.BoardInput;
import zerobase.board.board.service.BoardService;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String boardIndex() {
        return "board/index";
    }

    @GetMapping("/board/register")
    public String register() {
        return "board/register";
    }

    @PostMapping("/board/register")
    public String registerSubmit(Model model,
                                 BoardInput parameter) {

        boolean result = boardService.register(parameter);
        model.addAttribute("result", result);

        return "redirect:/board/register";
    }

    @GetMapping("/board/list")
    public String list(Model model) {

        List<Board> lists = boardService.list();
        model.addAttribute("lists", lists);

        return "/board/list";
    }

    @GetMapping("/board/detail") // localhost:8080/board/detail?id=1
    public String detail(Model model, Long id) {

        /*list 가 아니다!! 데이터 딱 한줄이 필요하므로!!*/
        Board detail = boardService.detail(id);
        model.addAttribute("detail", detail);

        return "/board/detail";
    }

    // 수정 버튼!!
    @GetMapping("/board/update") //*여기서 id값을 받음 detail.html에서 넘겨주는*//*
    public String update(Model model, Long id) {
        Board update = boardService.update(id);
        model.addAttribute("update", update);
        return "/board/update"; // 컨트롤러로 redirect
    }

    @PostMapping("/board/update")
    public String update(BoardInput parameter, Model model) {
        boolean update = boardService.update2(parameter);
        model.addAttribute("update", update);
        return "redirect:/board/list"; // 컨트롤러로 redirect
    }

    // 삭제 버튼!!
    @PostMapping("/board/delete")
    public String delete(BoardInput parameter) {
        boardService.delete(parameter.getId());
        return "redirect:/board/list"; // 컨트롤러로 redirect
    }
}

