/*
 * Copyright 2022-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.taskmetrics;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableTask
public class TaskMetricsApplication {

	private static final Log logger = LogFactory.getLog(TaskMetricsApplication.class);

	@Autowired
	public SimpleMeterRegistry simpleMeterRegistry;

	public static void main(String[] args) {
		SpringApplication.run(TaskMetricsApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				logger.info("Hello ApplicationRunner Metric's World");
			}
		};
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				logger.info("Hello CommandLineRunner Metric's World");
			}
		};
	}

	/**
	 * Prints the metrics as recorded in the simpleMeterRegistry.
	 */
	@AfterTask
	public void afterTask(TaskExecution taskExecution) {
		System.out.println(simpleMeterRegistry.getMetersAsString());
	}

}