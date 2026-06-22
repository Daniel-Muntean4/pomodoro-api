package com.Daniel_Muntean4.pomodoro_api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionsRepository extends JpaRepository<Session, Long> {
    @Query("SELECT s FROM Session s WHERE (:topicId IS NULL OR s.topicId = :topicId) AND (:after IS NULL OR s.startTime>=:after)")
    Page<Session> search(@Param("topicId") Long topicId,
                         @Param("after") Long after, Pageable pageable);

}
