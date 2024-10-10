/*
 * Copyright 2017-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.app.order;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
@Component
class EmailSender {

	@ApplicationModuleListener
	void on(OrderCompleted event) {
		sendEmailFor(event.orderIdentifier());
	}

	void sendEmailFor(UUID orderIdentifier) {

		log.info("Sending email for order {}.", orderIdentifier);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException o_O) {}

		// throw new RuntimeException();

		log.info("Email sent for order {}.", orderIdentifier);
	}
}
