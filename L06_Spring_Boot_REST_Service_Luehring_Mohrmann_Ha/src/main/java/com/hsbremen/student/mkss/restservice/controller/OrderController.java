package com.hsbremen.student.mkss.restservice.controller;

import java.util.List;

import com.hsbremen.student.mkss.restservice.eventsproducer.EventsProducer;
import com.hsbremen.student.mkss.restservice.model.Order;
import com.hsbremen.student.mkss.restservice.service.OrderService;
import com.hsbremen.student.mkss.restservice.util.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final OrderService orderService;

    private final EventsProducer eventsProducer;
    public OrderController(OrderService orderService, EventsProducer eventsProducer){
        this.orderService = orderService;
        this.eventsProducer = eventsProducer;
    }

    @GetMapping("/orders")
    public ResponseEntity listOrders() {
        List orders = this.orderService.findAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity getOrder(@PathVariable Integer orderId) {
        Order order = this.orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>("No order found with this id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}/items")
    public ResponseEntity getLineItems(@PathVariable Integer orderId) {
        Order order = this.orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>("No order found with this id.",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order.items, HttpStatus.OK);
    }

    @PostMapping("/orders") // POST for creating
    public ResponseEntity<Order> addOrder(@RequestBody OrderInputData orderInputData){
        Order order = new Order();
        order.setCustomerName(orderInputData.getCustomerName());
        this.orderService.saveOrder(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{orderId}/items")
    public ResponseEntity addItemToOrder(@PathVariable Integer orderId,@RequestBody OrderInputData orderInputData) {
        Order order = this.orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>("No order found with this id.", HttpStatus.NOT_FOUND);
        }
        order = orderService.addProduct(orderId, orderInputData);
        if(order == null) {
            return new ResponseEntity<>("Line items may only be added or removed as long as the order has not been committed",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/orders/{orderId}/{itemId}")  // Put for updating order
    public ResponseEntity deleteItemFromOrder(@PathVariable Integer orderId, @PathVariable Integer itemId){
        Order order = this.orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>("No order found with this id.", HttpStatus.NOT_FOUND);
        }
        order = this.orderService.deleteItem(orderId, itemId);
        if(order == null) {
            return new ResponseEntity<>("Line items may only be added or removed as long as the order has not been committed",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/orders/{orderId}/commit") // PutMapping because not completely processed yet
    public ResponseEntity commitOrder(@PathVariable Integer orderId) {
        Order order = this.orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>("No order found with this id.", HttpStatus.NOT_FOUND);
        }
        if (!order.status.equals(Status.IN_PREPARATION)){
            return new ResponseEntity<>("An order may only be purchased in its\n" +
                    "IN_PREPARATION status! ", HttpStatus.NOT_ACCEPTABLE);
        }
        order.setStatus(Status.COMMITTED);
        this.orderService.saveOrder(order);
        eventsProducer.emitCreateEvent(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable Integer orderId){
        Order order = this.orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>("No order found with this id.", HttpStatus.NOT_FOUND);
        }
        if(order.status.equals(Status.COMMITTED)) {
            return new ResponseEntity<>("Order may only be deleted when order has not been committed",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        this.orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
