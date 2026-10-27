package com.ranjankumarmandal.notification_service.entity;

import com.ranjankumarmandal.notification_service.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String subject;

	@Column(nullable = false, length = 2000)
	private String message;

	@Enumerated(EnumType.STRING)
	private NotificationType type;

	@Column
	private Boolean read;

	@Column
	private String recipient;
}
