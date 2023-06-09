package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     * 订单统计
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @return Result
     */
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> getOrderStatistic(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        return Result.success(reportService.getOrderStatistic(begin, end));
    }

    /**
     * 销量前10统计
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @return Result
     */
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> getTop10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        return Result.success(reportService.getTop10(begin, end));
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {

        reportService.exportExcel(response);
    }
}
