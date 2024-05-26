package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * employee management
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags="Employee APIs")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * login
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value="employee login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("employee login：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //after successful login，generate jwt
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
     * exit
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value="employee logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * new employee
     *
     * @return
     */
    @PostMapping
    @ApiOperation(value="new employee")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Created new employee: {}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * show employee list
     *
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value="show employee list")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("Employee list: {}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * modify employee status
     *
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value="modify employee status")
    public Result employeeStatus(Long id, @PathVariable Integer status) {
        log.info("Modify employee:{},{}", id, status);
        employeeService.employeeStatus(id, status);
        return Result.success();
    }
}
