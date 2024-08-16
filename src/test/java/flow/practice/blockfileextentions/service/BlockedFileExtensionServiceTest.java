package flow.practice.blockfileextentions.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import flow.practice.blockfileextentions.domain.dto.response.FixedExtensionsResponseDto;
import flow.practice.blockfileextentions.domain.entity.BlockedFileExtension;
import flow.practice.blockfileextentions.domain.repository.BlockedFileExtensionRepository;
import flow.practice.blockfileextentions.domain.service.BlockedFileExtensionService;
import flow.practice.blockfileextentions.fixture.BlockedFileExtensionFixture;
import flow.practice.blockfileextentions.global.error.ErrorCode;
import flow.practice.blockfileextentions.global.error.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
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

    @Test
    @DisplayName("고정된 확장자가 이미 존재하면 삭제한다.")
    void deleteFixedExtensionBeforeExisted() {
        //given
        Long fixedExtensionId = 1L;
        BlockedFileExtension blockedFileExtension = BlockedFileExtensionFixture.oneFixedFileExtension();
        given(
            blockedFileExtensionRepository.findById(fixedExtensionId)
        ).willReturn(Optional.of(blockedFileExtension));

        //when
        blockedFileExtensionService.changeFixedExtensionStatus(fixedExtensionId);

        //then
        assertThat(blockedFileExtension.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("고정된 확장자가 삭제되어 있으면 되살린다.")
    void reviveFixedExtensionBeforeDeleted() {
        //given
        Long fixedExtensionId = 1L;
        BlockedFileExtension blockedFileExtension = BlockedFileExtensionFixture.oneDeletedFixedFileExtension();
        given(
            blockedFileExtensionRepository.findById(fixedExtensionId)
        ).willReturn(Optional.of(blockedFileExtension));

        //when
        blockedFileExtensionService.changeFixedExtensionStatus(fixedExtensionId);

        //then
        assertThat(blockedFileExtension.getDeletedAt()).isNull();
    }

    @Test
    @DisplayName("고정된 확장자의 ID가 존재하지 않으면 예외가 발생한다.")
    void fixedExtensionThrowExceptionWhenNotExistId() {
        //given
        Long fixedExtensionId = 1L;
        given(
            blockedFileExtensionRepository.findById(fixedExtensionId)
        ).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() ->
            blockedFileExtensionService.changeFixedExtensionStatus(fixedExtensionId))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining(ErrorCode.ENTITY_NOT_FOUND_ERROR.getMessage());
    }

}
