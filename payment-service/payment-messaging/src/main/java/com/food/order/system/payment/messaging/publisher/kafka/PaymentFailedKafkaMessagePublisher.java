package com.food.order.system.payment.messaging.publisher.kafka;

import com.food.order.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.food.order.system.kafka.producer.KafkaMessageHelper;
import com.food.order.system.kafka.producer.service.KafkaProducer;
import com.food.order.system.payment.application.service.config.PaymentServiceConfigData;
import com.food.order.system.payment.application.service.ports.output.message.publisher.PaymentFailedMessagePublisher;
import com.food.order.system.payment.messaging.mapper.PaymentMessagingDataMapper;
import com.food.order.system.payment.service.domain.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentFailedKafkaMessagePublisher implements PaymentFailedMessagePublisher {

    private final PaymentMessagingDataMapper paymentDataMapper;
    private final KafkaProducer<String , PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;

    private final KafkaMessageHelper kafkaMessageHelper;


    @Override
    public void publish(PaymentFailedEvent event) {
        log.info("Publishing payment failed event to kafka");
        var orderId = event.getPayment().getOrderId().getValue().toString();
        try {
            var paymentResponseAvroModel =
                    paymentDataMapper.paymentFailedEventToPaymentResponseAvroModel(event);

            kafkaProducer.send(paymentServiceConfigData.getPaymentResponseTopicName(),
                    orderId,
                    paymentResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallBack(
                            paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            orderId,
                            "PaymentResponseAvroModel"));

            log.info("Published payment failed event to kafka");
        }   catch (Exception e) {
            log.error("Error while publishing payment failed event to kafka", e);
        }
    }
}
