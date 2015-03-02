package com.liztube.service;

import com.liztube.business.VideoBusiness;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;
import com.liztube.utils.facade.VideoCreationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Video API service which provides all videos necessary methods
 */
@RestController
@RequestMapping("/api/video")
public class VideoService {

    @Autowired
    VideoBusiness videoBusiness;

    /**
     * Upload a video AJOUTER LES DROITS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * @param file
     * @param videoCreationFacade
     */
    @RequestMapping(value="/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String uploadVideo(@RequestParam(value="file", required=true) MultipartFile file,
                           @RequestParam("videoCreationFacade") VideoCreationFacade videoCreationFacade) throws UserNotFoundException, VideoException {
        return videoBusiness.uploadVideo(file, videoCreationFacade);
    }

}
