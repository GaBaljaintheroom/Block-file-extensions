package flow.practice.blockfileextentions.domain.dto.response;

import lombok.Builder;

@Builder
public record CustomExtensionsResponseDto(
    Long id,
    String name
) {

}
