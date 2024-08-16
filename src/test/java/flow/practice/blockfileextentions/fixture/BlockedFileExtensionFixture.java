package flow.practice.blockfileextentions.fixture;

import flow.practice.blockfileextentions.domain.entity.BlockedFileExtension;
import java.util.List;
import java.util.stream.IntStream;

public class BlockedFileExtensionFixture {

    public static List<BlockedFileExtension> fixedFileExtensions(int count) {
        return IntStream.range(0, count)
            .mapToObj(i -> BlockedFileExtension.builder()
                .name("testName")
                .isFixed(true)
                .build())
            .toList();
    }

    public static BlockedFileExtension oneFixedFileExtension() {
        return BlockedFileExtension.builder()
            .name("testName")
            .isFixed(true)
            .build();
    }

    public static BlockedFileExtension oneDeletedFixedFileExtension() {
        BlockedFileExtension blockedFileExtension = BlockedFileExtension.builder()
            .name("testName")
            .isFixed(true)
            .build();

        blockedFileExtension.delete();
        return blockedFileExtension;
    }

}
