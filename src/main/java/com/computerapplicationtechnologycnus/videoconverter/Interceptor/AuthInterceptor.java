/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: MIT
 * See LICENSE-MIT file for full terms */
package com.computerapplicationtechnologycnus.videoconverter.Interceptor;


import com.computerapplicationtechnologycnus.videoconverter.Annotation.AuthRequired;
import com.computerapplicationtechnologycnus.videoconverter.Config.ProgramConfig;
import com.computerapplicationtechnologycnus.videoconverter.Exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private static String CONST_TOKEN = "";
    private static Boolean ENABLE_UACHECK = true;
    private static final String SAMPLE_TOKEN = "This_Is_Test_Token_Please_Replace_It_With_Strong_Random_String";

    private final ProgramConfig programConfig;

    public AuthInterceptor(ProgramConfig programConfig){
        this.programConfig=programConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("AuthInterceptor triggered for URL: " + request.getRequestURI());

        //读取Token和安全配置
        CONST_TOKEN = programConfig.getProgramToken();
        ENABLE_UACHECK = programConfig.getUseragent().isEnableChecker();


        if(CONST_TOKEN.equals(SAMPLE_TOKEN)){
            logger.error("You are Using a sample Token,that's UNSAFE!!! \n Check your Config immediately!!!");
            logger.error("您正在使用默认令牌，这是不安全的行为！！！\n请马上检查设置并更换令牌！！！");
        }
        // 检查 User-Agent
        if(ENABLE_UACHECK){
            String userAgent = request.getHeader("User-Agent");
            if (!isValidUserAgent(userAgent)) {
                logger.error("Invalid or suspicious User-Agent: " + userAgent);
                throw new AuthenticationException("Suspicious request detected!\n Please use a normal browser.");
            }
        }

        // 如果不是处理方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取方法上的 @AuthRequired 注解
        AuthRequired authRequired = handlerMethod.getMethodAnnotation(AuthRequired.class);

        // 如果方法没有标注 @AuthRequired 注解，跳过认证
        if (authRequired == null) {
            logger.info("No @AuthRequired annotation found, skipping authorization.");
            return true;
        }
        int minPermissionLevel = authRequired.minPermissionLevel();
        //调试用，获取方法最低所需权限
        logger.info("Required minPermissionLevel: " + minPermissionLevel);
        // 获取请求中的 token
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            logger.error("Missing token in request.");
            throw new AuthenticationException("Missing token");
        }
        try {
            // 提取 token（去掉 Bearer 前缀）
            String requestToken = token.startsWith("Bearer ") ? token.substring(7) : token;
//            String jwtToken = token;
            if (!CONST_TOKEN.equals(requestToken)) {
                logger.error("Invalid token！");
                throw new AuthenticationException("Invalid token: "+requestToken);
            }
            logger.info("Token is valid and permissions are sufficient.");
            return true;

        } catch (AuthenticationException e) {
            logger.error("Invalid token: " + e.getMessage());
            throw new AuthenticationException("Invalid token: " + e.getMessage());
        }
    }

    /**
     * UA检查器设定
     * @param userAgent
     * @return
     */
    private boolean isValidUserAgent(String userAgent) {
        if (userAgent == null || userAgent.isEmpty()) {
            logger.warn("No User-Agent provided");
            return false;
        }

        // 检查黑名单正则
        if (!programConfig.getUseragent().getBlacklist().isEmpty() &&
                userAgent.matches(programConfig.getUseragent().getBlacklist())) {
            logger.warn("User-Agent matched blacklist: {}", userAgent);
            return false;
        }

        // 检查白名单正则
        if (!programConfig.getUseragent().getWhitelist().isEmpty() &&
                !userAgent.matches(programConfig.getUseragent().getWhitelist())) {
            logger.warn("User-Agent not in whitelist: {}", userAgent);
            return false;
        }

        return true;
    }
}