package flow.practice.blockfileextentions.domain.service;

import flow.practice.blockfileextentions.domain.dto.response.FixedExtensionsResponseDto;
import flow.practice.blockfileextentions.domain.entity.BlockedFileExtension;
import flow.practice.blockfileextentions.domain.repository.BlockedFileExtensionRepository;
import flow.practice.blockfileextentions.global.error.exception.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockedFileExtensionService {

    private final BlockedFileExtensionRepository blockedFileExtensionRepository;

    public List<FixedExtensionsResponseDto> findFixedExtensions() {
        List<BlockedFileExtension> fixedExtensions =
            blockedFileExtensionRepository.findByIsFixedTrueAndDeletedAtIsNull();

        return fixedExtensions.stream()
            .map(fixedExtension -> FixedExtensionsResponseDto.builder()
                .id(fixedExtension.getId())
                .name(fixedExtension.getName())
                .build()
            )
            .toList();
    }

    public int getCustomExtensionsCount() {
        return blockedFileExtensionRepository.countByIsFixedFalseAndDeletedAtIsNull();
    }

    @Transactional
    public void changeFixedExtensionStatus(Long fixedExtensionId) {
        BlockedFileExtension blockedFileExtension = blockedFileExtensionRepository
            .findById(fixedExtensionId).orElseThrow(EntityNotFoundException::new);

        if (blockedFileExtension.isExisted()) {
            blockedFileExtension.delete();
            return;
        }

        blockedFileExtension.revive();
    }
}
