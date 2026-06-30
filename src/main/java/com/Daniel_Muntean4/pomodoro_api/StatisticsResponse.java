package com.Daniel_Muntean4.pomodoro_api;

import java.util.List;

public record StatisticsResponse(
        String favoriteTopic,

        long totalSessionsCount,
        long todayCount,
        long thisWeekCount,
        long thisMonthCount,

        double studyHours,
        double studyHoursToday,
        double studyHoursThisWeek,
        double studyHoursThisMonth,

        double averageSession,
        double averageSessionToday,
        double averageSessionThisWeek,
        double averageSessionThisMonth,

        int longestStreak,
        List<DailyStudy> last7Days
){}
