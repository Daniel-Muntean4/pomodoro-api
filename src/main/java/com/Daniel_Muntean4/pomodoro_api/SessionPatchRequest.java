package com.Daniel_Muntean4.pomodoro_api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SessionPatchRequest {

    private Long startTime;
    private Long stopTime;

    private Long duration;
    private Long topicId;


    public SessionPatchRequest() {

    }
}
