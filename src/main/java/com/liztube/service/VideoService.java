package com.liztube.service;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Video API service which provides all videos necessary methods
 */
@RestController
@RequestMapping("/api/video")
public class VideoService {

    @RequestMapping(value="/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public void UploadFile(@RequestParam(value="file", required=true) MultipartFile file) {
        String fileName=file.getOriginalFilename();
        System.out.println(fileName);
    }

}
