package com.food.order.system.payment.application.service.ports.output.message.publisher;

import com.food.order.system.payment.service.domain.event.PaymentCancelledEvent;
import com.food.order.sysyem.event.publisher.DomainEventPublisher;

public interface PaymentCancelledMessagePublisher extends DomainEventPublisher<PaymentCancelledEvent> {
}