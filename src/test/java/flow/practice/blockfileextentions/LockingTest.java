package flow.practice.blockfileextentions;

import static org.assertj.core.api.Assertions.assertThat;

import flow.practice.blockfileextentions.domain.dto.request.CustomExtensionNameRequestDto;
import flow.practice.blockfileextentions.domain.exception.CustomFileExtensionOverMaxCountException;
import flow.practice.blockfileextentions.domain.repository.BlockedFileExtensionRepository;
import flow.practice.blockfileextentions.domain.service.BlockedFileExtensionService;
import flow.practice.blockfileextentions.fixture.BlockedFileExtensionFixture;
import flow.practice.blockfileextentions.global.config.JpaAuditingConfig;
import flow.practice.blockfileextentions.global.error.ErrorCode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Import(value = JpaAuditingConfig.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LockingTest {

    @Autowired
    private BlockedFileExtensionRepository blockedFileExtensionRepository;

    @Autowired
    private BlockedFileExtensionService blockedFileExtensionService;

    @Test
    @DisplayName("쓰기 락 동시성 테스트")
    void concurrencyTest() {
        //given
        blockedFileExtensionRepository.saveAll(
            BlockedFileExtensionFixture.customFileExtensions(199)
        );

        //when & then
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    blockedFileExtensionService.addCustomExtension(
                        new CustomExtensionNameRequestDto("락락")
                    );
                } catch (CustomFileExtensionOverMaxCountException e) {
                    assertThat(e.getMessage()).isEqualTo(
                        ErrorCode.CUSTOM_FILE_EXTENSION_OVER_MAX_COUNT_ERROR.getMessage()
                    );

                    int actualCount = blockedFileExtensionRepository.countByIsFixedFalseAndDeletedAtIsNull();
                    assertThat(actualCount).isEqualTo(200);
                }
            });
        }
        executor.shutdown();
    }
}
