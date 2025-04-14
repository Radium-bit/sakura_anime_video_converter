package com.computerapplicationtechnologycnus.videoconverter.Config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
@Schema(description = "程序配置")
public class ProgramConfig {

    @Value("${program.enable-echo-config}")
    @Schema(description = "是否开启配置回显，可以检查当前程序配置")
    private boolean enableEchoConfig;
}
