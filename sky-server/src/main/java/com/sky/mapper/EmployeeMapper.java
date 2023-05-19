package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username 员工用户名
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 根据Id查询员工
     * @param id 员工Id
     * @return
     */
    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee getById(Long id);

    /**
     * 根据分页查询员工
     * @param name 员工姓名
     * @return
     */
    List<Employee> getByPage(String name);

    /**
     * 新增员工
     * @param employee
     */
    @Insert("INSERT INTO employee VALUES (null, #{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void saveEmployee(Employee employee);

    /**
     * 更新员工状态
     * @param status 员工状态
     * @param id 员工Id
     */
    @Update("UPDATE employee SET status = #{status} WHERE id = #{id}")
    void updateStatus(Integer status, Integer id);

    /**
     * 更新员工信息
     * @param employee
     */
    @Update("UPDATE employee SET name = #{name}, username = #{username}, phone = #{phone}, sex = #{sex}, id_number = #{idNumber}, update_time = #{updateTime} WHERE id = #{id}")
    void updateEmployee(Employee employee);

    /**
     * 更新员工密码
     * @param password 员工密码
     * @param id 员工Id
     */
    @Update("UPDATE employee SET password = #{password} WHERE id = #{id}")
    void updatePassword(String password, Long id);

}
