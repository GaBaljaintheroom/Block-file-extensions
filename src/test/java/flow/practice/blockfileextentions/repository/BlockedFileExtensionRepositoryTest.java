package flow.practice.blockfileextentions.repository;

import static org.assertj.core.api.Assertions.assertThat;

import flow.practice.blockfileextentions.QueryTest;
import flow.practice.blockfileextentions.domain.entity.BlockedFileExtension;
import flow.practice.blockfileextentions.domain.repository.BlockedFileExtensionRepository;
import flow.practice.blockfileextentions.fixture.BlockedFileExtensionFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BlockedFileExtensionRepositoryTest extends QueryTest {

    @Autowired
    private BlockedFileExtensionRepository blockedFileExtensionRepository;

    @Test
    @DisplayName("고정된 확장자 목록을 가져올 수 있다.")
    void findByIsFixedTrueAndDeletedAtIsNull() {
        //given
        List<BlockedFileExtension> fixedExtensions = BlockedFileExtensionFixture
            .fixedFileExtensions(3);
        blockedFileExtensionRepository.saveAll(fixedExtensions);

        //when
        int size = blockedFileExtensionRepository.findByIsFixedTrueAndDeletedAtIsNull().size();

        //then
        assertThat(size).isEqualTo(3);
    }

    @Test
    @DisplayName("고정된 확장자 목록이 없는 경우 빈 리스트를 반환한다.")
    void getEmptyListWhenNoneFixedExtensions() {
        //given
        List<BlockedFileExtension> customExtensions = BlockedFileExtensionFixture
            .customFileExtensions(3);
        blockedFileExtensionRepository.saveAll(customExtensions);

        //when
        List<BlockedFileExtension> actualList = blockedFileExtensionRepository.findByIsFixedTrueAndDeletedAtIsNull();

        //then
        assertThat(actualList).isEmpty();
    }

    @Test
    @DisplayName("커스텀 확장자의 총 개수를 가져올 수 있다.")
    void getCustomExtensionsTotalCount() {
        //given
        List<BlockedFileExtension> customExtensions = BlockedFileExtensionFixture
            .customFileExtensions(3);
        blockedFileExtensionRepository.saveAll(customExtensions);

        //when
        int actualCount = blockedFileExtensionRepository.countByIsFixedFalseAndDeletedAtIsNull();

        //then
        assertThat(actualCount).isEqualTo(3);
    }

}
