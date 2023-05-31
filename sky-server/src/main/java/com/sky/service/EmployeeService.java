package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 根据Id查询员工
     * @param id 员工Id
     * @return Employee
     */
    Employee getById(Long id);

    /**
     * 根据分页查询员工
     * @param name 员工姓名
     * @param page 页码
     * @param pageSize 分页大小
     * @return PageResult
     */
    PageResult getByPage(String name, Integer page, Integer pageSize);

    /**
     * 员工登录
     * @param employeeLoginDTO 员工登录DTO
     * @return Employee
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO 员工DTO
     */
    void saveEmployee(EmployeeDTO employeeDTO);

    /**
     * 更新员工状态
     * @param status 员工状态
     * @param id 员工Id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 更新员工信息
     * @param employeeDTO 员工DTO
     */
    void updateEmployee(EmployeeDTO employeeDTO);

    /**
     * 更新员工密码
     * @param passwordEditDTO 密码编辑DTO
     */
    void updatePassword(PasswordEditDTO passwordEditDTO);
}
