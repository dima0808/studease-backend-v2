package tech.studease.studease.common.event.message;

import tech.studease.studease.api.sessions.dto.TestSessionDto;

public record TimerMessage(TimerMessageType type, int timeLeft, TestSessionDto testSession) {

  public static TimerMessage of(TimerMessageType type, int timeLeft) {
    return new TimerMessage(type, timeLeft, null);
  }

  public static TimerMessage of(TimerMessageType type, int timeLeft, TestSessionDto testSession) {
    return new TimerMessage(type, timeLeft, testSession);
  }
}
