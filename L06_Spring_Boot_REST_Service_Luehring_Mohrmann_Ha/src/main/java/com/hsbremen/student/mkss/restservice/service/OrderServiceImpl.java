package com.hsbremen.student.mkss.restservice.service;

import com.hsbremen.student.mkss.restservice.model.LineItem;
import com.hsbremen.student.mkss.restservice.controller.OrderInputData;
import com.hsbremen.student.mkss.restservice.util.Status;
import com.hsbremen.student.mkss.restservice.model.Order;
import com.hsbremen.student.mkss.restservice.model.Product;
import com.hsbremen.student.mkss.restservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllOrders() throws DataAccessException {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(int id) throws DataAccessException {
        Order order = orderRepository.findById(id);
        return order;
    }

    @Override
    public List<LineItem> findAllItemsOfOrder(int orderId) throws DataAccessException {
        List<LineItem> items = null;
        try {
            items = orderRepository.findById(orderId).items;
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
        // just ignore not found exceptions for Jdbc/Jpa realization
        return null;
    }
        return items;
    }

    @Override
    public Order createNewOrder(String customerName) throws DataAccessException {
        Order order = new Order();
        order.setCustomerName(customerName);
        orderRepository.save(order);
        return order;
    }


    @Override
    public void deleteOrder(int orderId) throws DataAccessException {
        try {
            Order order = orderRepository.findById(orderId);
            orderRepository.delete(order);
        } catch (ObjectRetrievalFailureException | EmptyResultDataAccessException e) {
            // just ignore not found exceptions for Jdbc/Jpa realization
        }
    }

    @Override
    public void saveOrder(Order order) throws DataAccessException {
        orderRepository.save(order);
    }

    @Override
    public Order addProduct(int orderId, OrderInputData orderInputData) throws DataAccessException {
        Order order = findOrderById(orderId);
        if(order == null) {
            return null;
        }
        if(order.status.equals(Status.COMMITTED)) {
            return null;
        }
        Product item = new Product();
        item.setPrice(orderInputData.getPrice());
        item.setName(orderInputData.getName());
        item.setQuantity(orderInputData.getQuantity());
        order.setStatus(Status.IN_PREPARATION);
        order.addProduct(item);
        saveOrder(order);
        return order;
    }

    @Override
    public Order deleteItem(int orderId, Integer itemId) {
        Order order = findOrderById(orderId);
        if(order == null) {
            return null;
        }
        if(order.status.equals(Status.COMMITTED)) {
            return null;
        }
        order.deleteItem(itemId);
        saveOrder(order);
        return order;
    }


}
