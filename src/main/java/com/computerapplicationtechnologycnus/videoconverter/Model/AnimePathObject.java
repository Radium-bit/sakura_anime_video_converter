/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: MIT
 * See LICENSE-MIT file for full terms */

package com.computerapplicationtechnologycnus.videoconverter.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnimePathObject {
    @Schema(description = "动漫集数，剧场版直接写1，只有一集")
    private Long episodes;
    @Schema(description = "实际文件路径名")
    private String fileName;
}
