package flow.practice.blockfileextentions.global.error.exception;

import flow.practice.blockfileextentions.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends BusinessException {

    private final ErrorCode errorCode;

    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND_ERROR);
        this.errorCode = ErrorCode.ENTITY_NOT_FOUND_ERROR;
    }
}
