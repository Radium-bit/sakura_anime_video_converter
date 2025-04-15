/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: MIT
 * See LICENSE-MIT file for full terms */
package com.computerapplicationtechnologycnus.videoconverter.Config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@Schema(description = "程序配置")
@ConfigurationProperties(prefix = "program")
public class ProgramConfig {

    private UserAgent useragent = new UserAgent();

    @Schema(description = "是否开启配置回显，可以检查当前程序配置")
    private boolean enableEchoConfig;

    @Schema(description = "是否开启配置回显，可以检查当前程序配置")
    private String programToken;

    @Data
    public static class UserAgent {
        @Schema(description = "是否启用UA检查器")
        private boolean enableChecker;

        @Schema(description = "UA白名单正则")
        private String whitelist;

        @Schema(description = "UA黑名单")
        private String blacklist;
    }
}
