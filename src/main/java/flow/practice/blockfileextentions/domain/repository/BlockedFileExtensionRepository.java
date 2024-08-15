package flow.practice.blockfileextentions.domain.repository;

import flow.practice.blockfileextentions.domain.entity.BlockedFileExtension;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedFileExtensionRepository extends JpaRepository<BlockedFileExtension, Long> {
    List<BlockedFileExtension> findByIsFixedTrueAndDeletedAtIsNull();
}
