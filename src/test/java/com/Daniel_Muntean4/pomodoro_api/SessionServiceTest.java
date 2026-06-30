// file: src/test/java/com/Daniel_Muntean4/pomodoro_api/SessionServiceTest.java
package com.Daniel_Muntean4.pomodoro_api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionServiceTest{
    SessionService sessionService = new SessionService();
    @Test
    void patchPreservesNulls(){
        Session session = new Session();
        session.setId(1L);
        session.setStartTime(1000L);
        session.setStopTime(2000L);
        session.setDuration(1000L);
        session.setTopicId(2L);
        SessionPatchRequest sessionPatchRequest = new SessionPatchRequest();

        assertEquals(2000L, sessionService.patch(session,sessionPatchRequest).getStopTime());
    }
    @Test
    void patchChangesResource(){
        Session session = new Session();
        session.setStartTime(1000L);
        SessionPatchRequest sessionPatchRequest = new SessionPatchRequest();
        sessionPatchRequest.setStartTime(2000L);

        assertEquals(2000L, sessionService.patch(session,sessionPatchRequest).getStartTime());
    }
}