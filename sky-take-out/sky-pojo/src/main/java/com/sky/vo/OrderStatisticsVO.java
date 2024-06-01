package com.sky.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderStatisticsVO implements Serializable {
    // confirm number
    private Integer toBeConfirmed;

    // under delivery number
    private Integer confirmed;

    // delivery number
    private Integer deliveryInProgress;
}
