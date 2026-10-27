package com.ranjankumarmandal.order_service.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchScheduler {

	private final JobLauncher jobLauncher;
	private final Job processPendingOrdersJob;

	@Scheduled(cron = "0 */5 * * * ?")
	public void runPendingOrdersJob() {
		try {
			JobParameters params = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.toJobParameters();

			jobLauncher.run(processPendingOrdersJob, params);
			log.info("Batch job for processing pending orders started successfully");
		} catch (Exception e) {
			log.error("Error running batch job", e);
		}
	}
}
