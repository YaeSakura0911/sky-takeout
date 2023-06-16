package com.sky.service;

import com.sky.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     * 导出Excel报表
     *
     * @param response
     */
    void exportExcel(HttpServletResponse response) throws IOException;
}
