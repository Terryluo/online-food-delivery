package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "Setmeal APIs")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * new setmeal
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("new setmeal")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.addWithDish(setmealDTO);
        return Result.success();
    }
}