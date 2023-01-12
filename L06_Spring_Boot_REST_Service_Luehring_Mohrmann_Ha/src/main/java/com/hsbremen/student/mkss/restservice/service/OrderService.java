package com.hsbremen.student.mkss.restservice.service;

import com.hsbremen.student.mkss.restservice.model.LineItem;
import com.hsbremen.student.mkss.restservice.controller.OrderInputData;
import com.hsbremen.student.mkss.restservice.model.Order;
import org.springframework.dao.DataAccessException;

import java.util.List;


public interface OrderService {
    List<Order> findAllOrders() throws DataAccessException;
    Order findOrderById(int id) throws DataAccessException;

    Iterable<LineItem> findAllItemsOfOrder(int orderId) throws DataAccessException;

    Order createNewOrder(String customerName) throws DataAccessException;

    void deleteOrder(int orderId) throws DataAccessException;

    void saveOrder(Order order) throws DataAccessException;

    Order addProduct(int orderId, OrderInputData orderInputData) throws DataAccessException;

    Order deleteItem(int orderId, Integer itemId);
}
