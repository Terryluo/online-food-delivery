package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportVO implements Serializable {

    private String dateList;

    //order count list like: 260,210,215
    private String orderCountList;

    //valid order count list like：20,21,10
    private String validOrderCountList;

    private Integer totalOrderCount;

    private Integer validOrderCount;

    private Double orderCompletionRate;

}
