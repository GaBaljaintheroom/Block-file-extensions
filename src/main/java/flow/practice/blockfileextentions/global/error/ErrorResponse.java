package flow.practice.blockfileextentions.global.error;


import java.util.Collections;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public record ErrorResponse(
    String code,

    String message,

    List<Error> result
) {

    public static ErrorResponse fromErrorCode(ErrorCode errorCode) {
        return new ErrorResponse(
            errorCode.getCode(),
            errorCode.getMessage(),
            Collections.emptyList()
        );
    }

    public static ErrorResponse ofBindingResult(
        ErrorCode errorCode,
        BindingResult bindingResult
    ) {
        return new ErrorResponse(
            errorCode.getCode(),
            errorCode.getMessage(),
            Error.fromBindingResult(bindingResult)
        );
    }

    public static ErrorResponse fromParameter(
        ErrorCode errorCode,
        String parameterName
    ) {
        return new ErrorResponse(
            errorCode.getCode(),
            errorCode.getMessage(),
            List.of(Error.fromParameter(parameterName))
        );
    }

    public static ErrorResponse fromType(
        ErrorCode errorCode,
        String parameterName,
        String value
    ) {
        return new ErrorResponse(
            errorCode.getCode(),
            errorCode.getMessage(),
            List.of(Error.fromType(parameterName, value))
        );
    }

    public record Error(
        String field,
        String value,
        String reason
    ) {

        private static List<Error> fromBindingResult(BindingResult bindingResult) {
            return bindingResult.getFieldErrors()
                .stream()
                .map(Error::fromFieldError)
                .toList();
        }

        private static Error fromParameter(String parameterName) {
            return new Error(parameterName, "", "필수 입력 파라미터를 포함하지 않았습니다.");
        }

        private static Error fromType(String parameterName, String value) {
            return new Error(parameterName, value, "입력 파라미터의 타입이 올바르지 않습니다.");
        }

        private static Error fromFieldError(FieldError fieldError) {
            return new Error(
                fieldError.getField(),
                String.valueOf(fieldError.getRejectedValue()),
                fieldError.getDefaultMessage()
            );
        }

    }
}
