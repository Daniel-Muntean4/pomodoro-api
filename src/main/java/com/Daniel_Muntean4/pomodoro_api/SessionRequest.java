package com.Daniel_Muntean4.pomodoro_api;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SessionRequest {
    @NotNull private Long startTime;
    @NotNull private Long stopTime;
    @NotNull @Positive private Long duration;
    @NotNull private Long topicId;


    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }


    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

    public SessionRequest() {

    }
    @AssertTrue
    public boolean isTimeRangeValid(){
        if (stopTime==null || startTime==null) return true;
        return startTime<stopTime;
    }

    @AssertTrue
    public boolean isDurationValid(){
        if (duration==null ) return true;

        return duration<=3000000; // 50 minutes in ms
    }

}
