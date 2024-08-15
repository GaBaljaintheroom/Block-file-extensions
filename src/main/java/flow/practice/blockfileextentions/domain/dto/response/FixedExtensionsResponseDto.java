package flow.practice.blockfileextentions.domain.dto.response;

import lombok.Builder;

@Builder
public record FixedExtensionsResponseDto(
    Long id,
    String name
) {

}
