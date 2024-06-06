package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * turnover statistics
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        // create a datelist
        List<LocalDate> listDates = new ArrayList<>();
        listDates.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            listDates.add(begin);
        }
        String dateList = StringUtils.join(listDates, ",");

        List<Double> listOfTurnover = new ArrayList<>();
        for (LocalDate date : listDates) {
            // create turnover list
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            listOfTurnover.add(turnover);
        }

        String turnoverList = StringUtils.join(listOfTurnover, ",");
        return TurnoverReportVO.builder().dateList(dateList).turnoverList(turnoverList).build();
    }

    /**
     * user statistics
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserReportStatistics(LocalDate begin, LocalDate end) {
        // create a datelist
        List<LocalDate> listDates = new ArrayList<>();
        listDates.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            listDates.add(begin);
        }

        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();

        for (LocalDate date : listDates) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("end", endTime);
            Integer totalUser = userMapper.countByMap(map);

            map.put("begin", beginTime);
            Integer newUser = userMapper.countByMap(map);

            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(listDates, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    public OrderReportVO getOrderReportStatistics(LocalDate begin, LocalDate end) {
        // create a datelist
        List<LocalDate> listDates = new ArrayList<>();
        listDates.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            listDates.add(begin);
        }
        List<Integer> validOrderList = new ArrayList<>();
        List<Integer> totalOrderList = new ArrayList<>();

        for (LocalDate date : listDates) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Integer totalOrder = getOrderCount(beginTime, endTime, null);
            Integer validOrder = getOrderCount(beginTime, endTime, Orders.COMPLETED);

            totalOrderList.add(totalOrder);
            validOrderList.add(validOrder);
        }
        Integer validOrderCount = validOrderList.stream().reduce(Integer::sum).get();
        Integer totalOrderCount = totalOrderList.stream().reduce(Integer::sum).get();

        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }
        return OrderReportVO.builder()
                .dateList(StringUtils.join(listDates, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCountList(StringUtils.join(totalOrderList, ","))
                .validOrderCountList(StringUtils.join(validOrderList, ","))
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    @Override
    public SalesTop10ReportVO getSellingTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);


        List<GoodsSalesDTO> goodsSalesDTOS = orderMapper.getSellingTop10(beginTime, endTime);

        List<String> nameList = goodsSalesDTOS.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = goodsSalesDTOS.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());

        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }

    public void exportBusinessData(HttpServletResponse response) {
        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDate beginDate = endDate.minusDays(29);
        BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(beginDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX));

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/Export_Data_Template.xlsx");
        try {
            XSSFWorkbook excel = new XSSFWorkbook(in);
            // fill the sheet values
            XSSFSheet sheet = excel.getSheet("Sheet1");
            sheet.getRow(1).getCell(1).setCellValue("From: " + beginDate + " to " + endDate);

            XSSFRow row3 = sheet.getRow(3);
            row3.getCell(2).setCellValue(businessData.getTurnover());
            row3.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row3.getCell(6).setCellValue(businessData.getNewUsers());

            XSSFRow row4 = sheet.getRow(4);
            row4.getCell(2).setCellValue(businessData.getValidOrderCount());
            row4.getCell(4).setCellValue(businessData.getUnitPrice());

            // fill the Data Detail
            LocalDate date = beginDate;
            for (int i = 0; i < 30; i++) {
                BusinessDataVO everyDayData = workspaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));

                XSSFRow row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(everyDayData.getTurnover());
                row.getCell(3).setCellValue(everyDayData.getValidOrderCount());
                row.getCell(4).setCellValue(everyDayData.getOrderCompletionRate());
                row.getCell(5).setCellValue(everyDayData.getUnitPrice());
                row.getCell(6).setCellValue(everyDayData.getNewUsers());

                date = date.plusDays(1);
            }
            // create downloadable file
            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);

            outputStream.close();
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer getOrderCount(LocalDateTime begin, LocalDateTime end, Integer status) {
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", status);
        return orderMapper.countOrderByMap(map);
    }
}
