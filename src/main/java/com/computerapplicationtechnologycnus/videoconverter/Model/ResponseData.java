package com.computerapplicationtechnologycnus.videoconverter.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResponseData {

    @Schema(description = "文件转码存储的路径")
    private String storedPath;

    @Schema(description = "转码后文件名")
    private String fileName;
}
