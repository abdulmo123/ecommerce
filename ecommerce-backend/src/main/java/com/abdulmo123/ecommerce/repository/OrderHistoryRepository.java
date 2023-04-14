package com.abdulmo123.ecommerce.repository;

import com.abdulmo123.ecommerce.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

}
