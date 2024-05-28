package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * batch delete dish items
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete dish items")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("delete dishes: {}", ids);
        dishService.batchDelete(ids);
        return Result.success();
    }

    /**
     * get dish by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("get dish by id")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        log.info("get dish by id: {}", id);
        DishVO dishVo = dishService.getDishWithFlavorById(id);
        return Result.success(dishVo);
    }

    /**
     * modify dish
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("modifyDish")
    public Result modifyDish(@RequestBody DishDTO dishDTO) {
        log.info("modify dish: {}", dishDTO);
        dishService.modifyDishWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * modify dish status
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("modify dish status")
    public Result<String> modifyDishStatus(@PathVariable("status") Integer status, Long id){
        dishService.modifyDishStatus(status,id);
        return Result.success();
    }

    /**
     * search dish by category id
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("search dish by category id")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }
}
