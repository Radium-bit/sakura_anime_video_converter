/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: LGPL-3.0
 * See LICENSE-LGPL file for full terms */
package com.computerapplicationtechnologycnus.videoconverter.Handler;

import com.computerapplicationtechnologycnus.videoconverter.Common.ResultMessage;
import com.computerapplicationtechnologycnus.videoconverter.Exception.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //转为使用ResultMessage返回讯息
    @ExceptionHandler(AuthenticationException.class)
    public ResultMessage handleAuthenticationException(AuthenticationException e) {
        return ResultMessage.message(false,"",e.getMessage());
    }
}