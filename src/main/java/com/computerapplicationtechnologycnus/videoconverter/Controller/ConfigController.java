/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: MIT
 * See LICENSE-MIT file for full terms */
package com.computerapplicationtechnologycnus.videoconverter.Controller;

import com.computerapplicationtechnologycnus.videoconverter.Annotation.AuthRequired;
import com.computerapplicationtechnologycnus.videoconverter.Common.ResultMessage;
import com.computerapplicationtechnologycnus.videoconverter.Config.FFmpegConfig;
import com.computerapplicationtechnologycnus.videoconverter.Config.FileStorageProperties;
import com.computerapplicationtechnologycnus.videoconverter.Config.ProgramConfig;
import com.computerapplicationtechnologycnus.videoconverter.Interceptor.AuthInterceptor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
    private final FFmpegConfig ffmpegConfig;
    private final FileStorageProperties fileStorageProperties;
    private final ProgramConfig programConfig;

    public ConfigController(FFmpegConfig ffmpegConfig, FileStorageProperties fileStorageProperties, ProgramConfig programConfig) {
        this.ffmpegConfig = ffmpegConfig;
        this.fileStorageProperties = fileStorageProperties;
        this.programConfig = programConfig;
    }

    /**
     * 获取当前配置
     */
    @GetMapping("/getConfig")
    @AuthRequired
    public ResultMessage<ConfigSummary> getConfigs() {
        try {
            if (programConfig.isEnableEchoConfig()) {
                return ResultMessage.message(
                        new ConfigSummary(ffmpegConfig, fileStorageProperties, programConfig),
                        true, "获取配置成功");
            } else {
                return ResultMessage.message(false, "未允许获取配置");
            }
        } catch (Exception e) {
            return ResultMessage.message(false, "执行失败: " + e.getMessage());
        }
    }

    @Data
    public static class ConfigSummary {
        private final FFmpegConfig ffmpegConfig;
        private final FileStorageProperties fileConfig;
        private final ProgramConfig programConfig;
    }
}