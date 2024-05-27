package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dish")
@Api(tags="Dish APIs")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * Add new dish
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add new dish")
    public Result saveDish(@RequestBody DishDTO dishDTO) {
        log.info("Add new dish: {}", dishDTO);
        dishService.saveDish(dishDTO);
        return Result.success();
    }

    /**
     * show dishes list
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("show dishes list")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("show dishes list: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
}
