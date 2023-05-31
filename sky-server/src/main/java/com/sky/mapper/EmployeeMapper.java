package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
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
     * @return 员工Entity
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 根据Id查询员工
     * @param id 员工Id
     * @return 员工Entity
     */
    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee getById(Long id);

    /**
     * 根据分页查询员工
     * @param name 员工姓名
     * @return List<Employee> - 员工Entity列表
     */
    List<Employee> getByPage(String name);

    /**
     * 新增员工
     * @param employee 员工Entity
     */
    @AutoFill(OperationType.INSERT)
    @Insert("INSERT INTO employee VALUES (null, #{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void saveEmployee(Employee employee);

    // @Update("UPDATE employee SET status = #{status} WHERE id = #{id}")
    // void updateStatus(Integer status, Integer id);
    /**
     * 更新员工状态
     * @param employee 员工Entity
     */
    @AutoFill(OperationType.UPDATE)
    @Update("UPDATE employee SET status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} WHERE id = #{id}")
    void updateStatus(Employee employee);

    /**
     * 更新员工信息
     * @param employee 员工Entity
     */
    @AutoFill(OperationType.UPDATE)
    @Update("UPDATE employee SET name = #{name}, username = #{username}, phone = #{phone}, sex = #{sex}, id_number = #{idNumber}, update_time = #{updateTime} WHERE id = #{id}")
    void updateEmployee(Employee employee);


    // @Update("UPDATE employee SET password = #{password} WHERE id = #{id}")
    // void updatePassword(String password, Long id);
    /**
     * 更新员工密码
     *
     * @param employee 员工Entity
     */
    @AutoFill(OperationType.UPDATE)
    @Update("UPDATE employee SET password = #{password}, update_time = #{updateTime}, update_user = #{updateUser} WHERE id = #{id}")
    void updatePassword(Employee employee);

}
