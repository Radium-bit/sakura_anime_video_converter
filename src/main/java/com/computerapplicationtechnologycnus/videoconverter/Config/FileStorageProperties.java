/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: MIT
 * See LICENSE-MIT file for full terms */

package com.computerapplicationtechnologycnus.videoconverter.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class FileStorageProperties {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.prefix}")
    private String prefix;

}

