package com.onlinedelivery.service;

import com.onlinedelivery.dto.EmployeeLoginDTO;
import com.onlinedelivery.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
