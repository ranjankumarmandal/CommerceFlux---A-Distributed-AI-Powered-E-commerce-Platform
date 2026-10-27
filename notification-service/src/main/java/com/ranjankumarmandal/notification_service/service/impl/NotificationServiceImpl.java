package com.ranjankumarmandal.notification_service.service.impl;

import com.ranjankumarmandal.notification_service.dto.request.NotificationRequest;
import com.ranjankumarmandal.notification_service.dto.response.NotificationResponse;
import com.ranjankumarmandal.notification_service.entity.Notification;
import com.ranjankumarmandal.notification_service.enums.NotificationType;
import com.ranjankumarmandal.notification_service.exception.ResourceNotFoundException;
import com.ranjankumarmandal.notification_service.repository.NotificationRepository;
import com.ranjankumarmandal.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;

	@Override
	@Transactional
	public NotificationResponse send(NotificationRequest request) {
		Notification notification = Notification.builder()
				.userId(request.getUserId())
				.subject(request.getSubject())
				.message(request.getMessage())
				.type(NotificationType.valueOf(request.getType().toUpperCase()))
				.read(false)
				.recipient(request.getRecipient())
				.build();

		notification = notificationRepository.save(notification);
		return map(notification);
	}

	@Override
	public NotificationResponse getById(Long id) {
		Notification notification = notificationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
		return map(notification);
	}

	@Override
	public List<NotificationResponse> getUserNotifications(Long userId) {
		return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
				.map(this::map)
				.toList();
	}

	@Override
	@Transactional
	public NotificationResponse markAsRead(Long id) {
		Notification notification = notificationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
		notification.setRead(true);
		notification = notificationRepository.save(notification);
		return map(notification);
	}

	private NotificationResponse map(Notification notification) {
		return NotificationResponse.builder()
				.id(notification.getId())
				.userId(notification.getUserId())
				.subject(notification.getSubject())
				.message(notification.getMessage())
				.type(notification.getType().name())
				.read(notification.getRead())
				.recipient(notification.getRecipient())
				.createdAt(notification.getCreatedAt())
				.build();
	}
}
