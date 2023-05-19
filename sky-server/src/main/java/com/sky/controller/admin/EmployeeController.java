package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 根据Id查询员工
     * @param id 员工Id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {

        return Result.success(employeeService.getById(id));
    }

    /**
     * 根据分页查询员工
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> getByPage(String name, Integer page, Integer pageSize) {

        return Result.success(employeeService.getByPage(name, page, pageSize));
    }

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {

        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 员工登出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {

        // 从ThreadLocal中移除Id
        BaseContext.removeCurrentId();

        return Result.success();
    }

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @PostMapping
    public Result<String> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        employeeService.saveEmployee(employeeDTO);

        return Result.success();
    }

    /**
     * 更新员工状态
     * @param status 员工状态
     * @param id 员工Id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, Integer id) {

        employeeService.updateStatus(status, id);

        return Result.success();
    }

    /**
     * 更新员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    public Result<String> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {

        employeeService.updateEmployee(employeeDTO);

        return Result.success();
    }

    /**
     * 更新员工密码
     * @param passwordEditDTO
     * @return
     */
    @PutMapping("editPassword")
    public Result<String> updatePassword(@RequestBody PasswordEditDTO passwordEditDTO) {

        employeeService.updatePassword(passwordEditDTO);

        return Result.success();
    }

}
