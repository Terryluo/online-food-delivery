package com.sky.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.entity.User;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "order APIs")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * submit order
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("submit order")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * make order payment
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("make order payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("payment proceed: {}", ordersPaymentDTO);
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "ORDERPAID");

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        Integer OrderPaidStatus = Orders.PAID;
        Integer OrderStatus = Orders.TO_BE_CONFIRMED;

        LocalDateTime check_out_time = LocalDateTime.now();
        orderMapper.updateStatus(OrderStatus, OrderPaidStatus, check_out_time, ordersPaymentDTO.getOrderNumber());

        return Result.success(vo);
    }
    /*public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {
        OrderPaymentVO orderPaymentVO = OrderPaymentVO.builder().build();
        return Result.success(orderPaymentVO);
    }*/



    /**
     * order histories
     *
     * @param page
     * @param pageSize
     * @param status    1 pending payment 2. to be confirmed 3 confirmed 4 delivery in progress 5 complete 6 cancelled
     * @return
     */
    @GetMapping("/historyOrders")
    @ApiOperation("order histories")
    public Result<PageResult> page(int page, int pageSize, Integer status) {
        PageResult pageResult = orderService.pageQuery4User(page, pageSize, status);
        return Result.success(pageResult);
    }

    /**
     * order details
     *
     * @param id
     * @return
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("order details")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * cancel order
     *
     * @return
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("Cancel Order")
    public Result cancel(@PathVariable("id") Long id) throws Exception {
        orderService.userCancelById(id);
        return Result.success();
    }

    /**
     * Reorder
     *
     * @param id
     * @return
     */
    @PostMapping("/repetition/{id}")
    @ApiOperation("Reorder")
    public Result repetition(@PathVariable Long id) {
        orderService.repetition(id);
        return Result.success();
    }
}
