package flow.practice.blockfileextentions.domain.dto.request;

import flow.practice.blockfileextentions.domain.entity.BlockedFileExtension;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CustomExtensionNameRequestDto(

    @Length(min = 1, max = 20)
    @NotNull(message = "이름은 필수 요청값 입니다.")
    String name
) {

    public BlockedFileExtension toCustomExtension() {
        return BlockedFileExtension.builder()
            .name(name)
            .isFixed(false)
            .build();
    }

}
