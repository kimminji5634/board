package zerobase.board.board.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 추가하려면 NoArgs... AllArgs도 추가해야 함
@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Length(min=6) /*문자열 최소 최대길이 지정 - gradle에 validation 추가*/
    String title;

    @Length(min=2, max=10)
    String writer;

    @Length(min=15)
    String content;

    LocalDateTime regDt;
    LocalDateTime updDt;

}
