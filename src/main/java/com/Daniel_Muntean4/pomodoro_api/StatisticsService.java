package com.Daniel_Muntean4.pomodoro_api;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    private final ZoneId zone = ZoneId.of("Europe/Chisinau");
    private final TopicRepository topicRepository;
    private final SessionsRepository sessionsRepository;

    StatisticsService(TopicRepository topicRepository, SessionsRepository sessionsRepository) {
        this.topicRepository = topicRepository;
        this.sessionsRepository = sessionsRepository;
    }

    public StatisticsResponse compute() {
        List<Session> sessionList = sessionsRepository.findAll();
        String favoriteTopic;

        long totalSessionsCount;
        long todayCount;
        long thisWeekCount;
        long thisMonthCount;
        double studyHours;
        double studyHoursToday;
        double studyHoursThisWeek;
        double studyHoursThisMonth;
        Double averageSession;
        Double averageSessionToday;
        Double averageSessionThisWeek;
        Double averageSessionThisMonth;
        int longestStreak;
        LocalDate today = LocalDate.now(zone);
        long startMonthMs = today.withDayOfMonth(1).atStartOfDay(zone).toInstant().toEpochMilli();
        long startWeekMs = today.with(DayOfWeek.MONDAY).atStartOfDay(zone).toInstant().toEpochMilli();
        long startDayMs = today.atStartOfDay(zone).toInstant().toEpochMilli();
        long start7DaysMs = today.minusDays(6).atStartOfDay(zone).toInstant().toEpochMilli();

        totalSessionsCount = sessionsRepository.count();
        thisWeekCount = sessionsRepository.countByStartTimeGreaterThanEqual(startWeekMs);
        todayCount = sessionsRepository.countByStartTimeGreaterThanEqual(startDayMs);
        thisMonthCount = sessionsRepository.countByStartTimeGreaterThanEqual(startMonthMs);

        studyHours = toHours(sessionsRepository.totalDurationMs());
        studyHoursToday = toHours(sessionsRepository.totalDurationMsAfter(startDayMs));
        studyHoursThisWeek = toHours(sessionsRepository.totalDurationMsAfter(startWeekMs));
        studyHoursThisMonth = toHours(sessionsRepository.totalDurationMsAfter(startMonthMs));

        averageSession = toMinutes(sessionsRepository.avgDurationMs());
        averageSessionToday = toMinutes(sessionsRepository.avgDurationMsAfter(startDayMs));
        averageSessionThisWeek = toMinutes(sessionsRepository.avgDurationMsAfter(startWeekMs));
        averageSessionThisMonth = toMinutes(sessionsRepository.avgDurationMsAfter(startMonthMs));
        favoriteTopic = resolveFavoriteTopic();
        longestStreak = longestStreak(sessionsRepository.allStartTimes(), zone);
        List<DailyStudy> last7days = lastPeriodDays
                (sessionsRepository.startDurationAfter(start7DaysMs), today,zone,7);
        return new StatisticsResponse(
                favoriteTopic, totalSessionsCount, todayCount, thisWeekCount,
                thisMonthCount, studyHours, studyHoursToday, studyHoursThisWeek,
                studyHoursThisMonth, averageSession, averageSessionToday,
                averageSessionThisWeek, averageSessionThisMonth, longestStreak,last7days);
    }

    public static int longestStreak(List<Long> startTimes, ZoneId zone) {
        if (startTimes.isEmpty()) return 0;

        var days = startTimes.stream().map(ms -> Instant.ofEpochMilli(ms).atZone(zone)
                .toLocalDate()).distinct().sorted().toList();
        int best = 1, current = 1;
        for (int i = 1; i < days.size(); i++) {
            if (days.get(i - 1).plusDays(1).equals(days.get(i))) {
                current++;
                best = Math.max(best, current);
            } else {
                current = 1;
            }
        }
        return best;

    }

    private String resolveFavoriteTopic() {
        List<Long> ids = sessionsRepository.
                topicIdsByFrequency(PageRequest.of(0, 1));
        if (ids.isEmpty()) {
            return "-";
        }
        return topicRepository.findById(ids.getFirst()).
                map(Topic::getTopicName).orElse("-");
    }

    Double toHours(Long ms) {
        return Math.round((ms / 3_600_000.0) * 10) / 10.0;
    }

    Double toMinutes(Double ms) {
        return (ms == null) ? 0 : ms * 60_000D;
    }

    static List<DailyStudy> lastPeriodDays(List<StartDuration> rows, LocalDate today, ZoneId zone, int daysPeriod) {
      Map<LocalDate, Long> dateDuration = new HashMap<>();
      for(StartDuration r: rows) {
          LocalDate day = Instant.ofEpochMilli(r.startTime()).atZone(zone).toLocalDate();
          dateDuration.merge(day, r.duration()/60000, Long::sum);

      }
      List<DailyStudy> dailyStudyList = new ArrayList<>(daysPeriod);

        for(int i = daysPeriod-1; i<=0; i--){
            LocalDate day = LocalDate.now().minusDays(i);
            dailyStudyList.add(new DailyStudy(day.toString(), dateDuration.getOrDefault(day, 0L) ));
      }

      return dailyStudyList;
    }
}



