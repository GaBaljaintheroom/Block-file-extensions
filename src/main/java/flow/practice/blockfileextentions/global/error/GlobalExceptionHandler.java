package flow.practice.blockfileextentions.global.error;


import static flow.practice.blockfileextentions.global.error.ErrorCode.INPUT_INVALID_VALUE_ERROR;
import static flow.practice.blockfileextentions.global.error.ErrorCode.INTERNAL_SERVER_ERROR;
import static flow.practice.blockfileextentions.global.error.ErrorCode.REQUEST_PARAMETER_NOT_FOUND_ERROR;
import static flow.practice.blockfileextentions.global.error.ErrorCode.REQUEST_PARAMETER_TYPE_NOT_MATCH_ERROR;

import flow.practice.blockfileextentions.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR.getStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);

        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.fromErrorCode(errorCode);

        return ResponseEntity.status(errorCode.getStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleRequestArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        log.error(e.getMessage(), e);

        ErrorResponse response = ErrorResponse.ofBindingResult(
            INPUT_INVALID_VALUE_ERROR,
            e.getBindingResult()
        );
        return ResponseEntity.status(INPUT_INVALID_VALUE_ERROR.getStatus())
            .body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException e
    ) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.fromParameter(
            REQUEST_PARAMETER_NOT_FOUND_ERROR,
            e.getParameterName()
        );

        return ResponseEntity.status(REQUEST_PARAMETER_NOT_FOUND_ERROR.getStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e
    ) {
        log.error(e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.fromType(
            REQUEST_PARAMETER_TYPE_NOT_MATCH_ERROR,
            e.getParameter().getParameterName(),
            String.valueOf(e.getValue())
        );

        return ResponseEntity.status(REQUEST_PARAMETER_NOT_FOUND_ERROR.getStatus())
            .body(errorResponse);
    }
}
