package flow.practice.blockfileextentions.global.error.exception;

import flow.practice.blockfileextentions.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class LockConflictException extends BusinessException {

    private final ErrorCode errorCode;

    public LockConflictException() {
        super(ErrorCode.LOCK_CONFLICT_ERROR);
        this.errorCode = ErrorCode.LOCK_CONFLICT_ERROR;
    }
}
