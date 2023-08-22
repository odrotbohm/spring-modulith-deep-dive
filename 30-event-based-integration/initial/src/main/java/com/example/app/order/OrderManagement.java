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
package com.example.app.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.inventory.Inventory;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderManagement {

	private final OrderRepository orders;
	private final EmailSender emails;
	private final Inventory inventory;

	public Order findById(UUID id) {
		return orders.findById(id).orElse(null);
	}

	public void complete(Order order) {

		orders.save(order.complete());

		inventory.updateStockFor(order);
		emails.sendEmailFor(order.getId());

		log.info("Finish order completion.");
	}
}
