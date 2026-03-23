package tech.studease.studease.domain.sessions;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.studease.studease.domain.answers.Answer;
import tech.studease.studease.domain.questions.Question;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class ResponseEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private Question question;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Answer> answers;

  @ManyToOne(fetch = FetchType.EAGER)
  private TestSession testSession;
}
