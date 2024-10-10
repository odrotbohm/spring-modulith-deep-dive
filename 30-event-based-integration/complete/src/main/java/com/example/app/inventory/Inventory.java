/*
 * Copyright 2023-2024 the original author or authors.
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.order.Order;
import com.example.app.order.OrderCompleted;
import com.example.app.order.OrderManagement;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Inventory {

	private final OrderManagement orders;

	@ApplicationModuleListener
	void on(OrderCompleted event) {
		updateStockFor(orders.findById(event.orderIdentifier()));
	}

	public void updateStockFor(Order order) {

		log.info("Updating stock for order {}.", order.getId());

		// throw new RuntimeException();
	}
}
