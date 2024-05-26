package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * category management
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "Category APIs")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * new category
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("new category")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("new category：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * show category list
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("show category list")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("show category list：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * delete category
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete category")
    public Result<String> deleteById(Long id){
        log.info("delete category：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * modify category
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("modify category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * modify category status
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("modify category status")
    public Result<String> modifyCategoryStatus(@PathVariable("status") Integer status, Long id){
        categoryService.modifyCategoryStatus(status,id);
        return Result.success();
    }

    /**
     * search category by type
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("search category by type")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
