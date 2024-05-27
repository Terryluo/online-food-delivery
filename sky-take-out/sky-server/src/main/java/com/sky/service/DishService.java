package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DishService {
    public void saveDish(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void batchDelete(List<Long> ids);

    DishVO getDishWithFlavorById(Long id);

    void modifyDishWithFlavor(DishDTO dishDTO);

    void modifyDishStatus(Integer status, Long id);

    List<Dish> list(Long categoryId);
}
