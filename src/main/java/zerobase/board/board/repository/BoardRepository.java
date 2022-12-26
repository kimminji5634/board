package zerobase.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.board.board.entity.Board;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByTitle(String title);
    List<Board> findAll();

    // deleteById 기본 제공

    Optional<Board> findAllById(Long id);
}
