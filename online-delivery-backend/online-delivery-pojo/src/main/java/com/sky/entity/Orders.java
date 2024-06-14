package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * order
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {


    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;


    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    // order unique number
    private String number;

    // 1 pending payment 2. to be confirmed 3 confirmed 4 delivery in progress 5 complete 6 cancelled 7 refunded
    private Integer status;

    private Long userId;

    private Long addressBookId;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

    private Integer payMethod;

    // 0 unpaid 1 paid 2 refunded
    private Integer payStatus;

    private BigDecimal amount;

    private String remark;

    private String userName;

    private String phone;

    private String addressDetail;

    private String consignee;

    private String cancelReason;

    private String rejectionReason;

    private LocalDateTime cancelTime;

    private LocalDateTime estimatedDeliveryTime;

    //1 sent immediately  0 choose time
    private Integer deliveryStatus;

    private LocalDateTime deliveryTime;

    private int packAmount;

    private int tablewareNumber;

    // 1 follow by dishes/setmeal  0 follow by specified
    private Integer tablewareStatus;
}
