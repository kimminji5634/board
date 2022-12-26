package zerobase.board.board.model;

import lombok.Data;

@Data
public class BoardInput {

    long id;
    String title;
    String content;
    String writer;
}
