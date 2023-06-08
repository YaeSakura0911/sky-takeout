package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

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
}
