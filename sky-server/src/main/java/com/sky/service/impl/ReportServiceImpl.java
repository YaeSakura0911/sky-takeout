package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

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

            // 今日营业额
            BigDecimal totalAmount = new BigDecimal(0);

            // 拼接日期列表
            dateList.append(today);

            // 查询今日订单列表
            List<Orders> orders = orderMapper.selectByCompleteAndDate(today, today.plusDays(1));

            // 遍历订单列表
            for (Orders order : orders) {
                // 计算今日营业额
                totalAmount = totalAmount.add(order.getAmount());
            }

            // 拼接今日营业额
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

    /**
     * 订单统计
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return OrderReportVO
     */
    @Override
    public OrderReportVO getOrderStatistic(LocalDate beginDate, LocalDate endDate) {

        OrderReportVO orderReportVO = new OrderReportVO();

        StringBuilder dateList = new StringBuilder();
        StringBuilder orderCountList = new StringBuilder();
        StringBuilder validOrderCountList = new StringBuilder();

        LocalDate today = beginDate;
        // 订单总数
        Integer totalOrderCount = 0;
        // 有效订单总数
        Integer totalValidOrderCount = 0;


        while (!today.isEqual(endDate.plusDays(1))) {

            // 拼接日期列表
            dateList.append(today);

            // 查询今日有效订单数
            Integer validOrderCount = orderMapper.selectOrderCount(today, today.plusDays(1), Orders.COMPLETED);
            // 查询今日订单总数
            Integer orderCount = orderMapper.selectOrderCount(today, today.plusDays(1), null);

            // 计算订单总数
            totalOrderCount += orderCount;
            // 计算有效订单总数
            totalValidOrderCount += validOrderCount;

            // 拼接今日订单总数
            orderCountList.append(orderCount);
            // 拼接今日有效订单数
            validOrderCountList.append(validOrderCount);

            // 如果今天不是结束日期
            if (!today.equals(endDate)) {
                // 拼接逗号
                dateList.append(",");
                orderCountList.append(",");
                validOrderCountList.append(",");
            }

            today = today.plusDays(1);
        }

        // 设置日期列表
        orderReportVO.setDateList(dateList.toString());
        // 设置每日订单数
        orderReportVO.setOrderCountList(orderCountList.toString());
        // 设置每日有效订单数
        orderReportVO.setValidOrderCountList(validOrderCountList.toString());
        // 设置订单总数
        orderReportVO.setTotalOrderCount(totalOrderCount);
        // 设置有效订单总数
        orderReportVO.setValidOrderCount(totalValidOrderCount);
        // 设置订单完成率
        orderReportVO.setOrderCompletionRate(totalValidOrderCount.doubleValue() / totalOrderCount.doubleValue());

        return orderReportVO;
    }

    /**
     * 销量前10统计
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return SalesTop10ReportVO
     */
    @Override
    public SalesTop10ReportVO getTop10(LocalDate beginDate, LocalDate endDate) {

        SalesTop10ReportVO salesTop10ReportVO = new SalesTop10ReportVO();

        // StringBuilder nameList = new StringBuilder();
        // StringBuilder numberList = new StringBuilder();

        List<GoodsSalesDTO> goodsSalesDTOList = orderDetailMapper.selectTop10(beginDate, endDate);

        String nameList = StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList()), ",");
        String numberList = StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList()), ",");

        salesTop10ReportVO.setNameList(nameList);
        salesTop10ReportVO.setNumberList(numberList);

        return salesTop10ReportVO;
    }
}
