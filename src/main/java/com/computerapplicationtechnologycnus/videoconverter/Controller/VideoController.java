/* Copyright (c) 2025 Radium-bit
 * SPDX-License-Identifier: MIT
 * See LICENSE-MIT file for full terms */

package com.computerapplicationtechnologycnus.videoconverter.Controller;

import com.computerapplicationtechnologycnus.videoconverter.Common.ResultMessage;
import com.computerapplicationtechnologycnus.videoconverter.Config.FileStorageProperties;
import com.computerapplicationtechnologycnus.videoconverter.Model.ResponseData;
import com.computerapplicationtechnologycnus.videoconverter.Service.VideoService;
import com.computerapplicationtechnologycnus.videoconverter.Utils.FileTypeUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@RestController
public class VideoController {

    private final VideoService videoService;
    private final FileTypeUtil fileTypeUtil;
    private final FileStorageProperties fileStorageProperties;

    // 构造函数注入依赖（省略@Autowired，假设使用构造器注入）
    public VideoController(VideoService videoService, FileTypeUtil fileTypeUtil, FileStorageProperties fileStorageProperties) {
        this.videoService = videoService;
        this.fileTypeUtil = fileTypeUtil;
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * 视频文件上传接口
     */
    @PostMapping("/uploadAnime")
    public ResultMessage<ResponseData> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "subfile", required = false) MultipartFile subtitleFile) {

        try {
            // 验证视频文件
            if (file.isEmpty()) {
                return ResultMessage.message(false, "文件不能为空！");
            }
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !fileTypeUtil.isVideoFile(originalFilename)) {
                return ResultMessage.message(false, "仅支持上传视频文件（mp4, mkv, avi, mov）！");
            }

            // 处理字幕文件
            boolean hasSubtitle = subtitleFile != null && !subtitleFile.isEmpty();
            if (hasSubtitle) {
                String subFilename = subtitleFile.getOriginalFilename();
                if (!fileTypeUtil.isSubtitleFile(subFilename)) {
                    return ResultMessage.message(false, "仅支持上传字幕文件（txt, ass, vtt, srt）！");
                }
            }

            // 生成唯一文件名和存储路径
            String UploadDirProperties = fileStorageProperties.getUploadDir();
            String Prefix = fileStorageProperties.getPrefix();
            String uploadDir = UploadDirProperties + Prefix +"/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileExt = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String uniqueName = Prefix + "_" + System.currentTimeMillis();
            String videoPath = uploadDir + uniqueName + fileExt;
            file.transferTo(new File(videoPath));

            // 处理字幕并转码
            if (hasSubtitle) {
                String subExt = subtitleFile.getOriginalFilename().substring(subtitleFile.getOriginalFilename().lastIndexOf('.'));
                String subPath = uploadDir + uniqueName + subExt;
                subtitleFile.transferTo(new File(subPath));
                videoService.convertVideoToM3u8AddSubtitle(videoPath, subPath);
            } else {
                videoService.convertVideoToM3u8(videoPath);
            }
            ResponseData responseData = new ResponseData();
            responseData.setFileName(uniqueName);
            responseData.setStoredPath(uploadDir);
            return ResultMessage.message(responseData,true, "上传完成，转码中。");

        } catch (Exception e) {
            return ResultMessage.message(false, "视频上传失败！请联系管理员。", e.getMessage());
        }
    }
}

