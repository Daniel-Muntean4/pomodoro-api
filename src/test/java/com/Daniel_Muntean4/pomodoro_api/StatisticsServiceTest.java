// file: src/test/java/com/Daniel_Muntean4/pomodoro_api/StatisticsServiceTest.java
package com.Daniel_Muntean4.pomodoro_api;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsServiceTest {
    private static final ZoneId ZONE = ZoneId.of("Europe/Chisinau");
    private  static final Long DAY = 86_400_000L; // ms a day
    @Test
    void emptyIsZero(){
        assertEquals(0, StatisticsService.longestStreak(List.of(),ZONE));
    }
    @Test
    void oneConsecutiveIsThree(){
        assertEquals(1, StatisticsService.longestStreak(List.of(DAY*22), ZONE));
    }
    @Test
    void threeConsecutiveIsThree(){
        assertEquals(3, StatisticsService.longestStreak(List.of(DAY*20, DAY*21,DAY*22), ZONE));
    }
    @Test
    void gapResetStreak(){
        assertEquals(3, StatisticsService.longestStreak(List.of(DAY*17*DAY*18*DAY*20, DAY*21,DAY*22), ZONE));
    }
    @Test
    void SameDayDuplicatesCollapse(){
        assertEquals(1L,
                StatisticsService.longestStreak(
                        List.of(DAY*3+72_000L, DAY*3+4_440_000L, DAY*3+65_880_000L),ZONE));
    }
}