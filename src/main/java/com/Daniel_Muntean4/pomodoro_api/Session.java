package com.Daniel_Muntean4.pomodoro_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity

public class Session
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long startTime;
    private Long stopTime;
    private Long duration;
    private Long topicId;

    public SessionResponse toResponse(){
         return new SessionResponse
                (this.getId(),
                        this.getStartTime(),
                        this.getStopTime(),
                        this.getDuration(),
                        this.getTopicId());
    }

    public Session() {

    }
}
