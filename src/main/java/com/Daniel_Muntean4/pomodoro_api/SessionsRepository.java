package com.Daniel_Muntean4.pomodoro_api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionsRepository extends JpaRepository<Session, Long> {
    @Query("SELECT s FROM Session s WHERE (:topicId IS NULL OR s.topicId = :topicId) AND (:after IS NULL OR s.startTime>=:after)")
    Page<Session> search(@Param("topicId") Long topicId,
                         @Param("after") Long after, Pageable pageable);
    @Query("SELECT AVG(s.duration) FROM Session s")
    Double avgDurationMs();
    @Query("SELECT AVG(s.duration) FROM Session s WHERE s.startTime>=:after")
    Double avgDurationMsAfter(@Param("after") Long after);
    @Query("SELECT COALESCE(SUM (s.duration),0) FROM Session s")
    Long totalDurationMs();
    @Query("SELECT COALESCE(SUM (s.duration),0) FROM Session s WHERE s.startTime>=:after")
    Long totalDurationMsAfter(@Param("after") Long after);
    @Query("SELECT s.topicId FROM Session s GROUP BY s.topicId ORDER BY COUNT(s) DESC ")
    List<Long> topicIdsByFrequency(Pageable pageable);
    Long countByStartTimeGreaterThanEqual(@Param("after") Long after);
    @Query("SELECT s.startTime FROM Session s")
    List<Long> allStartTimes();
    @Query("SELECT new com.Daniel_Muntean4.pomodoro_api.StartDuration(s.startTime, s.duration) " +
            "FROM Session s WHERE s.startTime>=:after")
    List<StartDuration> startDurationAfter(@Param("after") Long after);

}