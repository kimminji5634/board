package zerobase.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.board.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
