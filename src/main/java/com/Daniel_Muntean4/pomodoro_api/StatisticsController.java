package com.Daniel_Muntean4.pomodoro_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@Tag(name="Statistics", description = "Aggregate statistics for study sessions")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
    @Operation(summary = "Aggregate statistics across total or specified period")
    @GetMapping
    StatisticsResponse getStatistics(){
        return statisticsService.compute();
    }
}
