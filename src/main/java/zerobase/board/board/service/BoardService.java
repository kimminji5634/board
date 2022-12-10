package zerobase.board.board.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.board.board.entity.Board;
import zerobase.board.board.model.BoardInput;
import zerobase.board.board.repository.BoardRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    public boolean register(BoardInput parameter) {

        Board board = Board.builder()
                .title(parameter.getTitle())
                .content(parameter.getContent())
                .writer(parameter.getWriter())
                .regDt(LocalDateTime.now())
                .build();

        boardRepository.save(board);

        return true;
    }
}
