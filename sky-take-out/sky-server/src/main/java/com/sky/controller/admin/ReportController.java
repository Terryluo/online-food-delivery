package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Api(tags = "Report Apis")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * turnover statistics
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("turnover statistics")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
            log.info("turn over statistics: from {} to {}", begin, end);
            TurnoverReportVO turnoverReportVO = reportService.getTurnoverStatistics(begin, end);
            return Result.success(turnoverReportVO);
    }

    @GetMapping("/userStatistics")
    @ApiOperation("user statistics")
    public Result<UserReportVO> userReportStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("user statistics: from {} to {}", begin, end);
        UserReportVO userReportVO = reportService.getUserReportStatistics(begin, end);
        return Result.success(userReportVO);
    }

    @GetMapping("/ordersStatistics")
    @ApiOperation("order statistics")
    public Result<OrderReportVO> orderReportStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("order statistics: from {} to {}", begin, end);
        OrderReportVO orderReportVO = reportService.getOrderReportStatistics(begin, end);
        return Result.success(orderReportVO);
    }

    @GetMapping("/top10")
    @ApiOperation("selling top10")
    public Result<SalesTop10ReportVO> sellingTop10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("order statistics: from {} to {}", begin, end);
        SalesTop10ReportVO salesTop10ReportVO = reportService.getSellingTop10(begin, end);
        return Result.success(salesTop10ReportVO);
    }
}
