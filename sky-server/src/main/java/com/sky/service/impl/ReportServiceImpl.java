package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 用户统计
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return UserReportVO
     */
    @Override
    public UserReportVO getUserStatistic(LocalDate beginDate, LocalDate endDate) {

        UserReportVO userReportVO = new UserReportVO();

        StringBuilder dateList = new StringBuilder();
        StringBuilder totalUserList = new StringBuilder();
        StringBuilder newUserList = new StringBuilder();

        LocalDate today = beginDate;

        while (!today.isEqual(endDate.plusDays(1))) {

            // 拼接日期列表
            dateList.append(today);

            // TODO：应该还有优化的空间，不用每次循环都要查两遍
            // 查询昨日用户总量
            Integer yesterdayUserCount = userMapper.selectUserCountByDateTime(today);
            // 查询今日用户总量
            Integer todayUserCount = userMapper.selectUserCountByDateTime(today.plusDays(1));

            // 拼接用户总数列表
            totalUserList.append(todayUserCount);

            // 拼接新增用户数量列表
            newUserList.append(todayUserCount - yesterdayUserCount);

            // 如果今天不是结束日期
            if (!today.equals(endDate)) {
                // 拼接逗号
                dateList.append(",");
                totalUserList.append(",");
                newUserList.append(",");
            }

            today = today.plusDays(1);
        }

        // 设置日期列表
        userReportVO.setDateList(dateList.toString());

        // 设置用户总数列表
        userReportVO.setTotalUserList(totalUserList.toString());

        // 设置新增用户数量列表
        userReportVO.setNewUserList(newUserList.toString());

        return userReportVO;
    }

    /**
     * 营业额统计
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return TurnoverReportVO
     */
    @Override
    public TurnoverReportVO getTurnoverStatistic(LocalDate beginDate, LocalDate endDate) {

        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();

        StringBuilder dateList = new StringBuilder();
        StringBuilder turnoverList = new StringBuilder();

        LocalDate today = beginDate;


        while (!today.isEqual(endDate.plusDays(1))) {

            BigDecimal totalAmount = new BigDecimal(0);

            // 拼接日期列表
            dateList.append(today);

            List<Orders> orders = orderMapper.selectByCompleteAndDate(today, today.plusDays(1));

            for (Orders order : orders) {
                totalAmount = totalAmount.add(order.getAmount());
            }

            turnoverList.append(totalAmount);

            // 如果今天不是结束日期
            if (!today.equals(endDate)) {
                // 拼接逗号
                dateList.append(",");
                turnoverList.append(",");
            }

            today = today.plusDays(1);
        }

        turnoverReportVO.setDateList(dateList.toString());
        turnoverReportVO.setTurnoverList(turnoverList.toString());

        return turnoverReportVO;
    }
}
