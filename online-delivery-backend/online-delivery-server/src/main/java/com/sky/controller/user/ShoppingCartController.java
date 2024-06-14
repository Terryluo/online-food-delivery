package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "User shopping cart APIs")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * add dishes/setmeals to cart
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("Add dishes/setmeal to cart")
    public Result addToCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("Add to shopping cart: {}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * show shopping cart
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("show shopping cart")
    public Result<List<ShoppingCart>> shoppingCartList() {
        List<ShoppingCart> list = shoppingCartService.shoppingCartList();
        return Result.success(list);
    }

    /**
     * delete all item from shopping cart
     * @return
     */
    @DeleteMapping("/clean")
    @ApiOperation("delete all item from shopping cart")
    public Result deleteAll() {
        shoppingCartService.deleteAll();
        return Result.success();
    }
}
