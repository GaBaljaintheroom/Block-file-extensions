package flow.practice.blockfileextentions.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import flow.practice.blockfileextentions.domain.dto.response.FixedExtensionsResponseDto;
import flow.practice.blockfileextentions.domain.repository.BlockedFileExtensionRepository;
import flow.practice.blockfileextentions.domain.service.BlockedFileExtensionService;
import flow.practice.blockfileextentions.fixture.BlockedFileExtensionFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BlockedFileExtensionServiceTest {

    private final BlockedFileExtensionRepository blockedFileExtensionRepository = mock(
        BlockedFileExtensionRepository.class
    );

    private final BlockedFileExtensionService blockedFileExtensionService = new BlockedFileExtensionService(
        blockedFileExtensionRepository
    );

    @Test
    @DisplayName("고정된 확장자 목록을 찾을 수 있다.")
    void findFixedExtensions() {
        //given
        given(
            blockedFileExtensionRepository.findByIsFixedTrueAndDeletedAtIsNull()
        ).willReturn(
            BlockedFileExtensionFixture.fixedFileExtensions(3)
        );

        //when
        List<FixedExtensionsResponseDto> fixedExtensions = blockedFileExtensionService.findFixedExtensions();

        //then
        assertThat(fixedExtensions.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("커스텀 확장자의 개수를 확인할 수 있다.")
    void getCustomExtensionsCount() {
        //given
        given(
            blockedFileExtensionRepository.countByIsFixedFalseAndDeletedAtIsNull()
        ).willReturn(10);

        //when
        int customExtensionsCount = blockedFileExtensionService.getCustomExtensionsCount();

        //then
        assertThat(customExtensionsCount).isEqualTo(10);
    }
}
