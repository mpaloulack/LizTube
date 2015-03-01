package com.liztube.business;

import com.liztube.entity.UserLiztube;
import com.liztube.exception.UserNotFoundException;
import com.liztube.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Business of video entity
 */
@Component
public class VideoBusiness {

    @Autowired
    AuthBusiness authBusiness;
    @Autowired
    VideoRepository videoRepository;

    /**
     *  Upload a video : validity checks, save file, create db entry
     * @param file
     * @param title
     * @param description
     * @param isPublic
     * @param isPublicLink
     */
    public String uploadVideo(MultipartFile file, String title, String description, boolean isPublic, boolean isPublicLink) throws UserNotFoundException {

        //Check validity of the file : video, mp4, not exceed 500Mo

        //Check validity of extra data : title, description, ...

        //Check user rights : connected

        //Save video file

        //Create db entry for the video (generate key associated to the video)

        //Send back the video key


        //UserLiztube userLiztube = authBusiness.getConnectedUser();

        return file.getOriginalFilename();
    }

}
