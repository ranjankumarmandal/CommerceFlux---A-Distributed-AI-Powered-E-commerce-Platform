package com.ranjankumarmandal.notification_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
	private Long id;
	private Long userId;
	private String subject;
	private String message;
	private String type;
	private Boolean read;
	private String recipient;
	private LocalDateTime createdAt;
}
