package com.ranjankumarmandal.order_service.batch;

import com.ranjankumarmandal.order_service.entity.Order;
import com.ranjankumarmandal.order_service.enums.OrderStatus;
import com.ranjankumarmandal.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OrderProcessingBatchConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final OrderRepository orderRepository;

	@Bean
	public Job processPendingOrdersJob() {
		return jobBuilderFactory.get("processPendingOrdersJob")
				.incrementer(new RunIdIncrementer())
				.start(processPendingOrdersStep())
				.build();
	}

	@Bean
	public Step processPendingOrdersStep() {
		return stepBuilderFactory.get("processPendingOrdersStep")
				.<Order, Order>chunk(10)
				.reader(pendingOrderReader())
				.processor(orderProcessor())
				.writer(orderWriter())
				.build();
	}

	@Bean
	@StepScope
	public ItemReader<Order> pendingOrderReader() {
		Map<String, Sort.Direction> sorts = new HashMap<>();
		sorts.put("createdAt", Sort.Direction.ASC);

		return new RepositoryItemReaderBuilder<Order>()
				.name("pendingOrderReader")
				.repository(orderRepository)
				.methodName("findAll")
				.sorts(sorts)
				.pageSize(10)
				.build();
	}

	@Bean
	public ItemProcessor<Order, Order> orderProcessor() {
		return order -> {
			log.info("Processing order: {}", order.getOrderNumber());
			if (order.getStatus() == OrderStatus.PENDING) {
				order.setStatus(OrderStatus.PROCESSING);
			}
			return order;
		};
	}

	@Bean
	public ItemWriter<Order> orderWriter() {
		return orders -> {
			for (Order order : orders) {
				orderRepository.save(order);
				log.info("Updated order status to PROCESSING: {}", order.getOrderNumber());
			}
		};
	}
}
