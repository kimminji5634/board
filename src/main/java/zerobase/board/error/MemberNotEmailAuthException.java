package zerobase.board.error;

public class MemberNotEmailAuthException extends RuntimeException {
    public MemberNotEmailAuthException(String error) {
        super(error);
    }
}
