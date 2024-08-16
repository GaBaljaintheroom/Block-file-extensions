package flow.practice.blockfileextentions.fixture;

import flow.practice.blockfileextentions.domain.dto.request.CustomExtensionNameRequestDto;

public class BlockedFileExtensionDtoFixture {

    public static CustomExtensionNameRequestDto customExtensionNameRequestDto() {
        return new CustomExtensionNameRequestDto("testName");
    }
}
