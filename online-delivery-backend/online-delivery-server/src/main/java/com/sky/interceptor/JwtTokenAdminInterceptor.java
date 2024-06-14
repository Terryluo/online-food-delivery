package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * json web token interceptor
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * compare jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //checked whether handler is from controller or not
        if (!(handler instanceof HandlerMethod)) {
            //if it is not the handler method, no need to check jwt
            return true;
        }

        //1、obtain jwt from header
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //2、check jwt
        try {
            log.info("jwt:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("current employee ID：", empId);
            BaseContext.setCurrentId(empId);
            //3、pass the interceptor
            return true;
        } catch (Exception ex) {
            //4、doesn't pass the check, return 401
            response.setStatus(401);
            return false;
        }
    }
}
