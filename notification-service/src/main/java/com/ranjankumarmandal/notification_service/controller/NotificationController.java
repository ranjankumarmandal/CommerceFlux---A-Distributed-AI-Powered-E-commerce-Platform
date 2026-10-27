package com.ranjankumarmandal.notification_service.controller;

import com.ranjankumarmandal.notification_service.dto.request.NotificationRequest;
import com.ranjankumarmandal.notification_service.dto.response.ApiResponse;
import com.ranjankumarmandal.notification_service.dto.response.NotificationResponse;
import com.ranjankumarmandal.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<NotificationResponse>> send(@RequestBody NotificationRequest request) {
		return ResponseEntity.ok(
				ApiResponse.ok(notificationService.send(request), "Notification sent successfully")
		);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<NotificationResponse>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(notificationService.getById(id), "Notification fetched successfully")
		);
	}

	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUserNotifications(@PathVariable Long userId) {
		return ResponseEntity.ok(
				ApiResponse.ok(notificationService.getUserNotifications(userId), "Notifications fetched successfully")
		);
	}

	@PutMapping("/{id}/read")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<NotificationResponse>> markAsRead(@PathVariable Long id) {
		return ResponseEntity.ok(
				ApiResponse.ok(notificationService.markAsRead(id), "Notification marked as read")
		);
	}
}
