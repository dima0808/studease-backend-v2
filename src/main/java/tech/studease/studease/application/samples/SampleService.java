package tech.studease.studease.application.samples;

import java.util.UUID;
import tech.studease.studease.api.samples.dto.SampleListDto;

public interface SampleService {

  SampleListDto findByTestId(UUID testId);
}
