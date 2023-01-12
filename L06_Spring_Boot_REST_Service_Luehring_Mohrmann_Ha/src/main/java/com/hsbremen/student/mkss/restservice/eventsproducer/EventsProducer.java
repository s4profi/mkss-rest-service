package com.hsbremen.student.mkss.restservice.eventsproducer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.hsbremen.student.mkss.restservice.model.Order;
import de.hsbremen.mkss.events.CrudEventProducer;
import de.hsbremen.mkss.events.EventWithPayload;
import de.hsbremen.mkss.events.Event;


@Component
public class EventsProducer implements CrudEventProducer<Order> {

    private AmqpTemplate amqpTemplate;

    @Value("${my.rabbitmq.an.exchange}")
    String anExchangeName;

    @Value("${my.rabbitmq.a.routing.key}")
    String aRoutingKeyName;


    public EventsProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }


    private EventWithPayload<Order> buildEvent(Event.EventType type, Order payload) {
        EventWithPayload<Order> event = EventWithPayload.<Order> builder()
                .type(type)
                .payload(payload)
                .build();
        return event;
    }

    @Override
    public void emitCreateEvent(Order payload) {
        EventWithPayload<Order> event = buildEvent(Event.EventType.CREATED, payload);

        // TODO: send event to RabbitMQ exchange

        amqpTemplate.convertAndSend(anExchangeName, aRoutingKeyName, event);

        System.out.println("Sent event = " + event + " using exchange " + anExchangeName + " with routing key " + aRoutingKeyName);
    }

    @Override
    public void emitUpdateEvent(Order payload) {
        // TODO: Implementation for update events (e.g. changed order)
    }

    @Override
    public void emitDeleteEvent(Order payload) {
        // TODO: Implementation for delete events (e.g. deleted order)
    }
}
