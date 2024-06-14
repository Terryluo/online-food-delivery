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
public class TurnoverReportVO implements Serializable {

    //date like：2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    // selling like：406.0,1520.0,75.0
    private String turnoverList;

}
