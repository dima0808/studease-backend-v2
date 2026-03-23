package tech.studease.studease.domain.sessions;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {

  @NonNull
  @EntityGraph(
      attributePaths = {
        "test",
        "test.questions",
        "test.samples",
        "responses",
        "responses.question",
        "responses.question.answers",
        "responses.answers"
      })
  Optional<TestSession> findById(@NonNull Long id);

  Optional<TestSession> findTestSessionByStudentGroupAndStudentNameAndTestId(
      String studentGroup, String studentName, UUID testId);

  boolean existsByStudentGroupAndStudentNameAndTestId(
      String studentGroup, String studentName, UUID testId);

  List<TestSession> findTestSessionsByTestId(UUID testId);
}
