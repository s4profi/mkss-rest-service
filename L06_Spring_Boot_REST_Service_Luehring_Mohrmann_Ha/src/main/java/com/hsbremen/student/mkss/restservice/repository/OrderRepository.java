package com.hsbremen.student.mkss.restservice.repository;

import com.hsbremen.student.mkss.restservice.model.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findById(long id) throws DataAccessException;

    List<Order> findAll() throws DataAccessException;
}