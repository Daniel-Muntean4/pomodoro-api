package com.Daniel_Muntean4.pomodoro_api;

public record SessionResponse(
          Long id,
         Long startTime,
         Long stopTime,
         Long duration,
         Long topicId) {
}
