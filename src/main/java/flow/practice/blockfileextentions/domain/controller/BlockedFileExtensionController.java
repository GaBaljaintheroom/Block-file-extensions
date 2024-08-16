package flow.practice.blockfileextentions.domain.controller;

import flow.practice.blockfileextentions.domain.dto.response.FixedExtensionsResponseDto;
import flow.practice.blockfileextentions.domain.service.BlockedFileExtensionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blocked-file")
@RequiredArgsConstructor
public class BlockedFileExtensionController {

    private final BlockedFileExtensionService blockedFileExtensionService;

    @GetMapping("/extensions")
    public String extensions(Model model) {
        List<FixedExtensionsResponseDto> fixedExtensions = blockedFileExtensionService.findFixedExtensions();
        List<String> fixedExtensionNames = fixedExtensions.stream()
            .map(FixedExtensionsResponseDto::name)
            .toList();

        int customExtensionsCount = blockedFileExtensionService.getCustomExtensionsCount();

        model.addAttribute("fixedExtensionStatus", fixedExtensionNames);
        model.addAttribute("customExtensionsCount", customExtensionsCount);
        return "extension";
    }

    @PostMapping("/{fixedExtensionId}")
    public ResponseEntity<Void> changeFixedExtensionStatus(
        @PathVariable("fixedExtensionId") Long fixedExtensionId
    ) {
        blockedFileExtensionService.changeFixedExtensionStatus(fixedExtensionId);

        return ResponseEntity.ok().build();
    }

}
