package flow.practice.blockfileextentions.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //global
    INTERNAL_SERVER_ERROR(500, "GLOBAL-001", "서버에 오류가 발생하였습니다."),
    INPUT_INVALID_VALUE_ERROR(400, "GLOBAL-002", "잘못된 입력 값입니다."),
    REQUEST_PARAMETER_NOT_FOUND_ERROR(400, "GLOBAL-004", "입력 파라미터가 존재하지 않습니다."),
    REQUEST_PARAMETER_TYPE_NOT_MATCH_ERROR(400, "GLOBAL-005", "입력 파라미터의 타입이 올바르지 않습니다."),
    LOCK_CONFLICT_ERROR(500, "GLOBAL-006", "락 획득에 실패했습니다."),

    //DB
    ENTITY_NOT_FOUND_ERROR(404, "DB-001", "요청한 엔티티를 찾을 수 없습니다."),

    //blockedFileExtension
    CUSTOM_FILE_EXTENSION_OVER_MAX_COUNT_ERROR(400, "BF-001", "커스텀 파일 확장자의 개수가 최대치를 초과했습니다."),
    CUSTOM_FILE_EXTENSION_DUPLICATED_ERROR(400, "BF-002", "커스텀 파일 확장자명이 중복되었습니다.");


    private final int status;
    private final String code;
    private final String message;
}
