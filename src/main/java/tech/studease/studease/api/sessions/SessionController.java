package tech.studease.studease.api.sessions;

import static org.springframework.util.StringUtils.hasText;
import static tech.studease.studease.common.util.CsvGeneratorUtils.generateCsv;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.studease.studease.api.sessions.dto.TestSessionListDto;
import tech.studease.studease.api.tests.dto.TestInfo;
import tech.studease.studease.application.sessions.TestSessionService;
import tech.studease.studease.application.tests.TestService;
import tech.studease.studease.domain.users.Credentials;

@RestController
@RequestMapping("/api/v1/admin/sessions")
@RequiredArgsConstructor
public class SessionController {

  private final TestSessionService testSessionService;
  private final TestService testService;

  @GetMapping("{testId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<TestSessionListDto> getFinishedSessionsByTestId(
      @PathVariable UUID testId,
      @RequestParam(required = false) String studentGroup,
      @RequestParam(required = false) String studentName) {
    if (hasText(studentGroup) && hasText(studentName)) {
      Credentials credentials = new Credentials(studentGroup, studentName);
      return ResponseEntity.ok(testSessionService.findByTestIdAndCredentials(testId, credentials));
    } else {
      return ResponseEntity.ok(testSessionService.findByTestId(testId));
    }
  }

  @GetMapping("{testId}/csv")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<byte[]> getFinishedSessionsByTestIdToCsv(@PathVariable UUID testId) {
    TestInfo test = testService.findById(testId);
    TestSessionListDto testSessionListDto = testSessionService.findByTestId(testId);

    byte[] csvBytes = generateCsv(testSessionListDto).getBytes();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment", "sessions_" + test.getName() + ".csv");
    return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
  }
}
