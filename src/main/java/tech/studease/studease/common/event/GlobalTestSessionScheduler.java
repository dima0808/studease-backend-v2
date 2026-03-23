package tech.studease.studease.common.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.studease.studease.api.sessions.dto.TestSessionDto;
import tech.studease.studease.application.sessions.impl.TestSessionServiceImpl;
import tech.studease.studease.common.event.message.TimerMessage;
import tech.studease.studease.common.event.message.TimerMessageType;

@Component
@RequiredArgsConstructor
public class GlobalTestSessionScheduler {

  private final SimpMessagingTemplate messagingTemplate;

  private static final Map<Long, Integer> testSessions = new ConcurrentHashMap<>();
  private final TestSessionServiceImpl testSessionService;

  @Scheduled(fixedRate = 1000)
  public void tick() {
    testSessions.forEach(
        (testSessionId, timeLeft) -> {
          if (timeLeft > 0) {
            testSessions.put(testSessionId, --timeLeft);
            notifyWithUpdatedTimer(testSessionId, timeLeft);
          } else {
            notifyTestSessionEnded(
                testSessionId, testSessionService.forceEndTestSession(testSessionId));
          }
        });
  }

  public static void addTimer(Long testSessionId, int durationInSeconds) {
    testSessions.put(testSessionId, durationInSeconds);
  }

  public static void removeTimer(Long testSessionId) {
    testSessions.remove(testSessionId);
  }

  private void notifyWithUpdatedTimer(Long testSessionId, int timeLeft) {
    messagingTemplate.convertAndSend(
        "/queue/testSession/" + testSessionId, TimerMessage.of(TimerMessageType.TIMER, timeLeft));
  }

  private void notifyTestSessionEnded(Long testSessionId, TestSessionDto testSessionDto) {
    messagingTemplate.convertAndSend(
        "/queue/testSession/" + testSessionId,
        TimerMessage.of(TimerMessageType.FORCE_END, 0, testSessionDto));
  }
}
