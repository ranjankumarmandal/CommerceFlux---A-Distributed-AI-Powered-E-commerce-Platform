package com.ranjankumarmandal.payment_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
	private Long paymentId;
	private String transactionId;
	private Long orderId;
	private Long customerId;
	private String eventType;
	private LocalDateTime timestamp;
	private Object payload;
}
