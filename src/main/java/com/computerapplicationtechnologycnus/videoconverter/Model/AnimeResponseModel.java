/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: MIT
 * See LICENSE-MIT file for full terms */

package com.computerapplicationtechnologycnus.videoconverter.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class AnimeResponseModel {
    @Schema(description = "默认ID")
    private Long id;

    @Schema(description = "动漫名称")
    private String name;

    @Schema(description = "标签，存储为JSON格式，这里直接用List了")
    private List<String> tags;

    @Schema(description = "动漫简介")
    private String description;

    @Schema(description = "评分，范围从1到10")
    private Float rating;

    @Schema(description = "发布日期")
    private Date releaseDate;

    @Schema(description = "视频文件集数和文件地址的列表")
    private List<AnimePathObject> filePath;
}

