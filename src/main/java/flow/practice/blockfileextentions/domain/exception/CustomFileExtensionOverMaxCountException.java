package flow.practice.blockfileextentions.domain.exception;

import flow.practice.blockfileextentions.global.error.ErrorCode;
import flow.practice.blockfileextentions.global.error.exception.BusinessException;

public class CustomFileExtensionOverMaxCountException extends BusinessException {

    public CustomFileExtensionOverMaxCountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
