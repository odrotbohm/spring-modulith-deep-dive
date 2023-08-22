package com.example.app.orders;

import java.util.UUID;

import org.jmolecules.event.annotation.Externalized;
import org.jmolecules.event.types.DomainEvent;

@Externalized("orders.OrderCompleted")
public record OrderCompleted(UUID id) implements DomainEvent {}
