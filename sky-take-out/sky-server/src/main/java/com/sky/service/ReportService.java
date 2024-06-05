package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    UserReportVO getUserReportStatistics(LocalDate begin, LocalDate end);

    OrderReportVO getOrderReportStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getSellingTop10(LocalDate begin, LocalDate end);
}
