package com.example.app.inventory;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "acme.inventory")
class InventoryProperties {

	/**
	 * The threshold to trigger a reordering
	 */
	int stockThreshold;
}
