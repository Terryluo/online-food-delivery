package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * search employee by username
     * @param username
     * @return
     */
    @Select("SELECT * FROM employee WHERE username = #{username}")
    Employee getByUsername(String username);

    /**
     * create new employee
     * @param employee
     * @return
     */
    @Insert("INSERT INTO employee (username, name, password, phone, gender, status, create_time, update_time, create_user, update_user) " +
            "VALUES " +
            "(#{username}, #{name}, #{password}, #{phone}, #{gender}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    /**
     * show employee list
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * update employee infomation
     * @param employee
     * @return
     */
    void update(Employee employee);
}
