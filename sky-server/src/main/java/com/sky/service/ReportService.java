package com.sky.service;

import com.sky.vo.*;

import java.time.LocalDate;

public interface ReportService {

    /**
     * 用户统计
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return UserReportVO
     */
    UserReportVO getUserStatistic(LocalDate beginDate, LocalDate endDate);

    /**
     * 营业额统计
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return TurnoverReportVO
     */
    TurnoverReportVO getTurnoverStatistic(LocalDate beginDate, LocalDate endDate);

    /**
     * 订单统计
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return OrderReportVO
     */
    OrderReportVO getOrderStatistic(LocalDate beginDate, LocalDate endDate);

    /**
     * 销量前10统计
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return SalesTop10ReportVO
     */
    SalesTop10ReportVO getTop10(LocalDate beginDate, LocalDate endDate);
}
