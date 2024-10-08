package flow.practice.blockfileextentions.domain.service;

import flow.practice.blockfileextentions.domain.dto.request.CustomExtensionNameRequestDto;
import flow.practice.blockfileextentions.domain.dto.response.CustomExtensionsResponseDto;
import flow.practice.blockfileextentions.domain.dto.response.FixedExtensionsResponseDto;
import flow.practice.blockfileextentions.domain.entity.BlockedFileExtension;
import flow.practice.blockfileextentions.domain.exception.CustomFileExtensionDuplicatedException;
import flow.practice.blockfileextentions.domain.exception.CustomFileExtensionOverMaxCountException;
import flow.practice.blockfileextentions.domain.repository.BlockedFileExtensionRepository;
import flow.practice.blockfileextentions.global.error.ErrorCode;
import flow.practice.blockfileextentions.global.error.exception.EntityNotFoundException;
import flow.practice.blockfileextentions.global.error.exception.LockConflictException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockedFileExtensionService {

    private final BlockedFileExtensionRepository blockedFileExtensionRepository;
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

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

    public List<CustomExtensionsResponseDto> findCustomExtensions() {
        List<BlockedFileExtension> customExtensions =
            blockedFileExtensionRepository.findByIsFixedFalseAndDeletedAtIsNull();

        return customExtensions.stream()
            .map(customExtension -> CustomExtensionsResponseDto.builder()
                .id(customExtension.getId())
                .name(customExtension.getName())
                .build())
            .toList();
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

    public void addCustomExtension(CustomExtensionNameRequestDto request) {
        Lock lock = reentrantReadWriteLock.writeLock();
        boolean lockAcquired = false;
        try {
            lockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
            if (lockAcquired) {
                addCustomExtensionTransactional(request);
            }
        } catch (InterruptedException e) {
            throw new LockConflictException();
        } finally {
            if (lockAcquired) {
                lock.unlock();
            }
        }
    }

    @Transactional
    public void addCustomExtensionTransactional(CustomExtensionNameRequestDto request) {
        int customFileExtensionCount = blockedFileExtensionRepository.countByIsFixedFalseAndDeletedAtIsNull();
        if (customFileExtensionCount == 200) {
            throw new CustomFileExtensionOverMaxCountException(
                ErrorCode.CUSTOM_FILE_EXTENSION_OVER_MAX_COUNT_ERROR
            );
        }

        boolean alreadyExist = blockedFileExtensionRepository.existsByNameAndDeletedAtIsNull(request.name());
        if (alreadyExist) {
            throw new CustomFileExtensionDuplicatedException(
                ErrorCode.CUSTOM_FILE_EXTENSION_DUPLICATED_ERROR
            );
        }

        blockedFileExtensionRepository.save(request.toCustomExtension());
    }

    @Transactional
    public void deleteCustomExtension(CustomExtensionNameRequestDto request) {
        BlockedFileExtension blockedFileExtension = blockedFileExtensionRepository
            .findByNameAndDeletedAtIsNull(request.name())
            .orElseThrow(EntityNotFoundException::new);

        blockedFileExtension.delete();
    }
}
