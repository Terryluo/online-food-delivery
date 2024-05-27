package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * search setmeal id by dish id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * insertbatch setmeal and dish
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
