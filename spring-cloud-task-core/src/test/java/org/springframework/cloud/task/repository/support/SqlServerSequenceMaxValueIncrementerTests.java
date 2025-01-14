/*
 * Copyright 2020-2020 the original author or authors.
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

package org.springframework.cloud.task.repository.support;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SqlServerSequenceMaxValueIncrementerTests {

	private ConfigurableApplicationContext context;

	@AfterEach
	public void tearDown() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void testDefaultDataSourceConfiguration() throws Exception {
		this.context = new AnnotationConfigApplicationContext(
				TaskExecutionDaoFactoryBeanTests.DefaultDataSourceConfiguration.class);

		DataSource dataSource = this.context.getBean(DataSource.class);

		SqlServerSequenceMaxValueIncrementer incrementer = new SqlServerSequenceMaxValueIncrementer(dataSource, "foo");
		assertThat(incrementer.getSequenceQuery()).isEqualTo("select next value for foo");
		assertThat(incrementer.getIncrementerName()).isEqualTo("foo");
	}

}
