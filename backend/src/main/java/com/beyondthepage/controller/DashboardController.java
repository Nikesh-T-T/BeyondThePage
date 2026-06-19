package com.beyondthepage.controller;

import com.beyondthepage.dto.response.ApiResponse;
import com.beyondthepage.dto.response.DailyBookProgressResponse;
import com.beyondthepage.dto.response.DashboardSummaryResponse;
import com.beyondthepage.dto.response.MonthlyDashboardResponse;
import com.beyondthepage.dto.response.WeeklyDashboardResponse;
import com.beyondthepage.service.DashboardService;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getSummary() {
		DashboardSummaryResponse summary = dashboardService.getSummary();
		return ResponseEntity.ok(ApiResponse.success("Dashboard summary fetched successfully", summary));
	}

	@GetMapping("/monthly")
	public ResponseEntity<ApiResponse<MonthlyDashboardResponse>> getMonthlyDashboard(
			@RequestParam YearMonth month) {
		MonthlyDashboardResponse response = dashboardService.getMonthlyDashboard(month);
		return ResponseEntity.ok(ApiResponse.success("Monthly dashboard fetched successfully", response));
	}

	@GetMapping("/weekly")
	public ResponseEntity<ApiResponse<WeeklyDashboardResponse>> getWeeklyDashboard(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		WeeklyDashboardResponse response = dashboardService.getWeeklyDashboard(date);
		return ResponseEntity.ok(ApiResponse.success("Weekly dashboard fetched successfully", response));
	}

	@GetMapping("/daily")
	public ResponseEntity<ApiResponse<List<DailyBookProgressResponse>>> getDailyDashboard(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		List<DailyBookProgressResponse> response = dashboardService.getDailyDashboard(date);
		return ResponseEntity.ok(ApiResponse.success("Daily dashboard fetched successfully", response));
	}
}
