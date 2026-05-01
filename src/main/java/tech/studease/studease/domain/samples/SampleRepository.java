package tech.studease.studease.domain.samples;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {

  List<Sample> findByTestId(UUID testId);

  boolean existsByCollectionIdAndTestIsNotNull(Long collectionId);

  boolean existsByCollectionIdInAndTestIsNotNull(List<Long> collectionIds);
}
