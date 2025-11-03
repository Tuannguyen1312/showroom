package com.showcar.showcar.controller;

import com.showcar.showcar.dto.StatisticsDTO;
import com.showcar.showcar.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * Lấy thống kê tổng quan cho dashboard
     * GET /api/statistics/dashboard
     * Required: ADMIN, MARKETING, SALES roles
     */
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'MARKETING', 'SALES')")
    public ResponseEntity<StatisticsDTO> getDashboardStatistics() {
        StatisticsDTO statistics = statisticsService.getDashboardStatistics();
        return ResponseEntity.ok(statistics);
    }
}

