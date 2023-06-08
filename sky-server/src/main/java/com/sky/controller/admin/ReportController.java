package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 用户统计
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @return Result
     */
    @GetMapping("/userStatistics")
    public Result<UserReportVO> getUserStatistic(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        return Result.success(reportService.getUserStatistic(begin, end));
    }

    /**
     * 营业额统计
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @return Result
     */
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> getTurnoverStatistic(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        return Result.success(reportService.getTurnoverStatistic(begin, end));
    }
}
