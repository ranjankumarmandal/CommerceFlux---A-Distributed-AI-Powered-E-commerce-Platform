package com.ranjankumarmandal.notification_service.service;

import com.ranjankumarmandal.notification_service.dto.request.NotificationRequest;
import com.ranjankumarmandal.notification_service.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
	NotificationResponse send(NotificationRequest request);
	NotificationResponse getById(Long id);
	List<NotificationResponse> getUserNotifications(Long userId);
	NotificationResponse markAsRead(Long id);
}
