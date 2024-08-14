package flow.practice.blockfileextentions.domain.entity;

import flow.practice.blockfileextentions.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "blocked_file_extension")
public class BlockedFileExtension extends BaseEntity {

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "is_fixed", nullable = false)
    private boolean isFixed = false;

}
