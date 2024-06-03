package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    // @Scheduled(cron = "0 * * * * ?") // every minute
    @Scheduled(cron = "1/5 * * * * ?") // every minute
    public void processOrderTimeout() {
        log.info("Processing order timeout: {}", LocalDateTime.now());

        LocalDateTime orderTimeLimit = LocalDateTime.now().plusMinutes(-15);
        List<Orders> timeoutOrders = orderMapper.getTimeOutOrderByStatusAndLimit(Orders.PENDING_PAYMENT, orderTimeLimit);

        if (timeoutOrders != null && timeoutOrders.size() > 0) {
            // once we get timeoutOrders, we cancel it
            for (Orders orders : timeoutOrders) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("Order timeout");
                orders.setCancelTime(LocalDateTime.now());

                orderMapper.update(orders);
            }
        }
    }

    //@Scheduled(cron = "0 0 1 * * ?") // everyday 1am execute the process
    @Scheduled(cron = "0/5 * * * * ?") // everyday 1am execute the process
    public void processDeliveryTimeout() {
        log.info("Processing order timeout: {}", LocalDateTime.now());

        LocalDateTime orderTimeLimit = LocalDateTime.now().plusHours(-1);
        List<Orders> timeoutDeliveries = orderMapper.getTimeOutOrderByStatusAndLimit(Orders.DELIVERY_IN_PROGRESS, orderTimeLimit);

        if (timeoutDeliveries != null && timeoutDeliveries.size() > 0) {
            for (Orders orders : timeoutDeliveries) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }

    }
}
