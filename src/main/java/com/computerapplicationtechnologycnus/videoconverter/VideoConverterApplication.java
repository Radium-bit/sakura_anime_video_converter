/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: MIT
 * See LICENSE-MIT file for full terms */

package com.computerapplicationtechnologycnus.videoconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan  // 确保启用 WebFilter
public class VideoConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoConverterApplication.class, args);
    }

}
