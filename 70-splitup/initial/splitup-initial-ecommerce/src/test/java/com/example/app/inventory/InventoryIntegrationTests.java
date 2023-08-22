/*
 * Copyright 2023 the original author or authors.
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
package com.example.app.inventory;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

import com.example.app.Infrastructure;
import com.example.app.inventory.Inventory.InventoryUpdated;
import com.example.app.orders.OrderCompleted;

/**
 * @author Oliver Drotbohm
 */
@ApplicationModuleTest
@Import(Infrastructure.class)
class InventoryIntegrationTests {

	@Test
	void updatesInventoryOnOrderCompletion(Scenario scenario) {

		scenario.publish(() -> new OrderCompleted(UUID.randomUUID()))
				.andWaitForEventOfType(InventoryUpdated.class)
				.toArrive();
	}
}
