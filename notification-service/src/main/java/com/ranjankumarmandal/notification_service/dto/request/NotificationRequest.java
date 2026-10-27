package com.ranjankumarmandal.notification_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
	@NotNull
	private Long userId;

	@NotBlank
	private String subject;

	@NotBlank
	private String message;

	@NotNull
	private String type;

	private String recipient;
}
