package tech.studease.studease.api.tests;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.studease.studease.api.tests.dto.TestDeleteRequestDto;
import tech.studease.studease.api.tests.dto.TestDto;
import tech.studease.studease.api.tests.dto.TestInfo;
import tech.studease.studease.api.tests.dto.TestListInfo;
import tech.studease.studease.application.tests.TestService;

@RestController
@RequestMapping("/api/v1/admin/tests")
@RequiredArgsConstructor
public class TestController {

  private final TestService testService;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<TestListInfo> getAllTests() {
    return ResponseEntity.ok(testService.findAll());
  }

  @GetMapping("{testId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<TestInfo> getTestInfoById(@PathVariable UUID testId) {
    return ResponseEntity.ok(testService.findById(testId));
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<TestInfo> createTest(@RequestBody @Valid TestDto testDto) {
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(testService.create(testDto));
  }

  @PutMapping("{testId}")
  @Deprecated
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<TestInfo> updateTest(
      @PathVariable UUID testId, @RequestBody @Valid TestDto testDto) {
    return ResponseEntity.ok(testService.update(testId, testDto));
  }

  @DeleteMapping("{testId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> deleteTest(@PathVariable UUID testId) {
    testService.deleteById(testId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> deleteTests(@RequestBody @Valid TestDeleteRequestDto deleteRequest) {
    testService.deleteAllByIds(deleteRequest);
    return ResponseEntity.noContent().build();
  }
}
