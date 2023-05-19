package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordEditFailedException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 根据Id查询员工
     * @param id 员工Id
     * @return
     */
    public Employee getById(Long id) {

        return employeeMapper.getById(id);
    }

    /**
     * 根据分页查询员工
     * @param name 员工姓名
     * @return
     */
    @Override
    public PageResult getByPage(String name, Integer page, Integer pageSize) {

        // 设置分页参数
        PageHelper.startPage(page, pageSize);

        // 执行条件分页查询
        List<Employee> employeeList = employeeMapper.getByPage(name);

        // 获取查询结果
        Page<Employee> employeePage = (Page<Employee>) employeeList;

        // 封装PageResult并返回
        return new PageResult(employeePage.getTotal(), employeePage.getResult());
    }

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {

        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        // 1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        // 2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）

        //账号不存在
        if (employee == null) {

            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 将密码进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        // 密码错误
        if (!password.equals(employee.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // 账号被锁定
        if (employee.getStatus() == StatusConstant.DISABLE) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        // 3、返回实体对象
        return employee;
    }

    /**
     * 保存员工信息
     * @param employeeDTO
     */
    @Override
    public void saveEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        // 设置密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes(StandardCharsets.UTF_8)));

        // 设置状态
        employee.setStatus(StatusConstant.ENABLE);

        // 设置创建、更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // 设置创建、更新人
        // employee.setCreateUser(1L);
        // employee.setUpdateUser(1L);
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.saveEmployee(employee);
    }

    /**
     * 更新员工状态
     *
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Integer id) {

        employeeMapper.updateStatus(status, id);
    }

    /**
     * 更新员工信息
     *
     * @param employeeDTO
     */
    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        // 设置更新时间
        employee.setUpdateTime(LocalDateTime.now());

        // 设置更新人
        employee.setUpdateUser(1L);

        employeeMapper.updateEmployee(employee);
    }

    /**
     * 更新员工密码
     * @param passwordEditDTO
     */
    @Override
    public void updatePassword(PasswordEditDTO passwordEditDTO) {

        Employee employee = employeeMapper.getById(BaseContext.getCurrentId());

        // 将旧密码转换成MD5
        String oldPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes(StandardCharsets.UTF_8));

        // 判断密码是否一致
        if (!oldPassword.equals(employee.getPassword())) {
            throw new PasswordEditFailedException(MessageConstant.PASSWORD_EDIT_FAILED);
        }

        // 将新密码转换成MD5
        String newPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes(StandardCharsets.UTF_8));

        employeeMapper.updatePassword(newPassword, employee.getId());
    }

}
