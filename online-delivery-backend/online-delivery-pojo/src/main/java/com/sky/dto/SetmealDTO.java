package com.sky.dto;

import com.sky.entity.SetmealDish;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {

    private Long id;

    private Long categoryId;

    private String name;

    private BigDecimal price;

    //0:Out of stock 1:selling
    private Integer status;

    private String description;

    private String image;

    private List<SetmealDish> setmealDishes = new ArrayList<>();

}
