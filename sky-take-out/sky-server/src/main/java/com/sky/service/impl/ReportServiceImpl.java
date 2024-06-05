package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

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
        String dateList = StringUtils.join(listDates, ",");

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
}
