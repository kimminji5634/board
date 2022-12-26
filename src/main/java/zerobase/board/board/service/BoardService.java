package zerobase.board.board.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.board.board.entity.Board;
import zerobase.board.board.model.BoardInput;
import zerobase.board.board.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    public boolean register(BoardInput parameter) {

        if (boardRepository.findByTitle(parameter.getTitle()).isPresent()) {
            return false;
        }

        Board board = Board.builder()
                .title(parameter.getTitle())
                .content(parameter.getContent())
                .writer(parameter.getWriter())
                .regDt(LocalDateTime.now())
                .build();

        boardRepository.save(board);

        return true;
    }

    public List<Board> list() {
        List<Board> list = boardRepository.findAll();
        return list;
    }

    public Board detail(Long id) {
        /*get 사용하는 이유는 findById가 Optional로 감싸져 있기 때문에 Optional 안에 값 가져오기 위해서*/
        Board detail = boardRepository.findAllById(id).get();
        return detail;
    }

    public Board update(Long id) {
        /*get 사용하는 이유는 findById가 Optional로 감싸져 있기 때문에 Optional 안에 값 가져오기 위해서*/
        Board update = boardRepository.findAllById(id).get();
        return update;
    }

    public boolean update2(BoardInput parameter) {
        Optional<Board> optionalBoard = boardRepository.findById(parameter.getId());

        //*Optional<Board> optionalBoard = boardRepository.findById(parameter.getId());
        if (optionalBoard.isPresent()) {

            Board board = optionalBoard.get();
            board.setTitle(parameter.getTitle());
            board.setContent(parameter.getContent());
            boardRepository.save(board);
        }

        return true;
    }

        /*public boolean update2(Long id, BoardInput parameter) {
            Optional<Board> optionalBoard = boardRepository.findAllById(id);

            //*Optional<Board> optionalBoard = boardRepository.findById(parameter.getId());
            if (optionalBoard.isPresent()) {

                Board board = optionalBoard.get();
                board.setTitle(parameter.getTitle());
                board.setContent(parameter.getContent());
                boardRepository.save(board);
            }

           return true;
        }*/

    public boolean delete(Long id) {
        boardRepository.deleteById(id);
        return true;
    }


}
