package com.Daniel_Muntean4.pomodoro_api;

import org.springframework.stereotype.Service;

@Service
public class SessionService {
    public Session mapRequestToEntity(SessionRequest request){
        Session entity = new Session();
        entity.setTopicId(request.getTopicId());
        entity.setStartTime(request.getStartTime());
        entity.setStopTime(request.getStopTime());
        entity.setDuration(request.getDuration());
        return entity;
    }
    public SessionResponse mapEntityToResponse(Session entity){
        return new SessionResponse
                        (entity.getId(),
                                entity.getStartTime(),
                                entity.getStopTime(),
                                entity.getDuration(),
                                entity.getTopicId());
    }
    public Session patch(Session session, SessionPatchRequest patch){
        if (patch.getTopicId() != null){
            session.setTopicId(patch.getTopicId());
        }
        if (patch.getDuration() != null){
            session.setDuration(patch.getDuration());
        }

        if (patch.getStartTime() != null){
            session.setStartTime(patch.getStartTime());
        }
        if (patch.getStopTime() != null){
            session.setStopTime(patch.getStopTime());
        }
        return session;

    }


}
