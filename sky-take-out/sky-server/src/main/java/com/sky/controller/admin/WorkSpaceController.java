package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * dashboard
 */
@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Api(tags = "dashboard Controller")
public class WorkSpaceController {

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * business data
     * @return
     */
    @GetMapping("/businessData")
    @ApiOperation("business data")
    public Result<BusinessDataVO> businessData(){

        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
        return Result.success(businessDataVO);
    }

    /**
     * overview orders
     * @return
     */
    @GetMapping("/overviewOrders")
    @ApiOperation("overview orders")
    public Result<OrderOverViewVO> orderOverView(){
        return Result.success(workspaceService.getOrderOverView());
    }

    /**
     * overview dishes
     * @return
     */
    @GetMapping("/overviewDishes")
    @ApiOperation("overview dishes")
    public Result<DishOverViewVO> dishOverView(){
        return Result.success(workspaceService.getDishOverView());
    }

    /**
     * overview setmeals
     * @return
     */
    @GetMapping("/overviewSetmeals")
    @ApiOperation("overview setmeals")
    public Result<SetmealOverViewVO> setmealOverView(){
        return Result.success(workspaceService.getSetmealOverView());
    }
}
