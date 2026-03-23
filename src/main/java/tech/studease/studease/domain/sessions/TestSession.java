package tech.studease.studease.domain.sessions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.studease.studease.domain.tests.Test;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class TestSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String studentGroup;
  private String studentName;

  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;

  private Integer currentQuestionIndex;

  private Integer mark;

  @OneToMany(
      mappedBy = "testSession",
      fetch = FetchType.EAGER,
      orphanRemoval = true,
      cascade = CascadeType.ALL)
  @OrderColumn
  private List<ResponseEntry> responses;

  @ManyToOne(fetch = FetchType.LAZY)
  private Test test;
}
