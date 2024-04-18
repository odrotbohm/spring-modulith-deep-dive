/*
 * Copyright 2017 the original author or authors.
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
package com.example.app.inventory;

import java.util.Optional;

import org.jmolecules.ddd.types.Association;
import org.springframework.data.repository.CrudRepository;

import com.example.app.catalog.Product;
import com.example.app.catalog.Product.ProductIdentifier;
import com.example.app.inventory.InventoryItem.InventoryItemIdentifier;

/**
 * @author Oliver Drotbohm
 */
public interface InventoryItemRepository extends CrudRepository<InventoryItem, InventoryItemIdentifier> {

	Optional<InventoryItem> findByProduct(Association<Product, ?> association);

	default Optional<InventoryItem> findByProductIdentifier(ProductIdentifier identifier) {
		return findByProduct(Association.forId(identifier));
	}
}
