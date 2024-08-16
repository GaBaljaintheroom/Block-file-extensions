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
        int actualSize = blockedFileExtensionRepository.findByIsFixedTrueAndDeletedAtIsNull().size();

        //then
        assertThat(actualSize).isEqualTo(3);
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

    @Test
    @DisplayName("커스텀 확장자 목록을 가져올 수 있다.")
    void findByIsFixedFalseAndDeletedAtIsNull() {
        //given
        List<BlockedFileExtension> customExtensions = BlockedFileExtensionFixture
            .customFileExtensions(3);
        blockedFileExtensionRepository.saveAll(customExtensions);

        //when
        int actualSize = blockedFileExtensionRepository.findByIsFixedFalseAndDeletedAtIsNull().size();

        //then
        assertThat(actualSize).isEqualTo(3);
    }

    @Test
    @DisplayName("커스텀 확장자 목록이 없는 경우 빈 리스트를 반환한다.")
    void getEmptyListWhenNoneCustomExtensions() {
        //given
        List<BlockedFileExtension> fixedFileExtensions = BlockedFileExtensionFixture
            .fixedFileExtensions(3);
        blockedFileExtensionRepository.saveAll(fixedFileExtensions);

        //when
        List<BlockedFileExtension> actualList = blockedFileExtensionRepository.findByIsFixedFalseAndDeletedAtIsNull();

        //then
        assertThat(actualList).isEmpty();
    }

    @Test
    @DisplayName("같은 이름이 존재하면 true를 반환한다.")
    void getTrueExistSameExtensionName() {
        //given
        BlockedFileExtension blockedFileExtension = BlockedFileExtensionFixture.oneFixedFileExtension();
        blockedFileExtensionRepository.save(blockedFileExtension);

        //when
        boolean actual = blockedFileExtensionRepository.existsByNameAndDeletedAtIsNull("testName");

        //then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("같은 이름이 존재하지 않으면 false를 반환한다.")
    void getFalseExistSameExtensionName() {
        //given
        BlockedFileExtension blockedFileExtension = BlockedFileExtensionFixture.oneFixedFileExtension();
        blockedFileExtensionRepository.save(blockedFileExtension);

        //when
        boolean actual = blockedFileExtensionRepository.existsByNameAndDeletedAtIsNull("박준수");

        //then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("이름이 같은 확장자를 가져올 수 있다.")
    void findByNameAndDeletedAtIsNull() {
        //given
        BlockedFileExtension blockedFileExtension = BlockedFileExtensionFixture.oneFixedFileExtension();
        blockedFileExtensionRepository.save(blockedFileExtension);

        //when
        BlockedFileExtension actualExtension = blockedFileExtensionRepository
            .findByNameAndDeletedAtIsNull("testName")
            .orElseThrow();

        //then
        assertThat(actualExtension.getName()).isEqualTo("testName");
    }

}
