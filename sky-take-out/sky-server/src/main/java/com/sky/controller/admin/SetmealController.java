package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * show setmeal list
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("show setmeal list")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * batch delete setmeal
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("batch delete setmeal")
    public Result batchDeleteSetmeal(@RequestParam List<Long> ids){
        setmealService.batchDeleteSetmeal(ids);
        return Result.success();
    }
}