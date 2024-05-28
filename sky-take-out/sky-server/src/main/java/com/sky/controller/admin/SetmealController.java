package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
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

    /**
     * get setmeal by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("get setmeal by id")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    /**
     * modify setmeal
     *
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("modify setmeal")
    public Result modifySetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.modifySetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * modify setmeal status
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("modify setmeal status")
    public Result<String> modifySetmealStatus(@PathVariable("status") Integer status, Long id){
        setmealService.modifySetmealStatus(status,id);
        return Result.success();
    }
}