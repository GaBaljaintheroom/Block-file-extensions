package flow.practice.blockfileextentions.domain.exception;

import flow.practice.blockfileextentions.global.error.ErrorCode;
import flow.practice.blockfileextentions.global.error.exception.BusinessException;

public class CustomFileExtensionDuplicatedException extends BusinessException {

    public CustomFileExtensionDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
