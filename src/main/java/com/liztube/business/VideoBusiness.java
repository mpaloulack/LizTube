package com.liztube.business;

import com.liztube.entity.UserLiztube;
import com.liztube.entity.Video;
import com.liztube.exception.SigninException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;
import com.liztube.repository.VideoRepository;
import com.liztube.utils.EnumError;
import com.liztube.utils.facade.VideoCreationFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Business of video entity
 */
@Component
public class VideoBusiness {

    @Autowired
    AuthBusiness authBusiness;
    @Autowired
    VideoRepository videoRepository;

    public ClassPathResource videoLibrary = new ClassPathResource("VideoLibrary/");

    @Autowired
    Environment environment;

    //region public methods
    /**
     *  Upload a video : validity checks, save file, create db entry
     * @param file
     * @param videoCreationFacade
     */
    public String uploadVideo(MultipartFile file, VideoCreationFacade videoCreationFacade) throws UserNotFoundException, VideoException {

        //Get connected user
        UserLiztube user = authBusiness.getConnectedUser();

        //Check validity of the file : video, mp4, not exceed 500Mo
        CheckVideoValidity(file);

        //Generate unique key
        String key = UUID.randomUUID().toString();

        //Check validity of extra data : title, description, ...
        Video video = new Video()
                .setTitle(videoCreationFacade.getTitle())
                .setDescription(videoCreationFacade.getDescription())
                .setIspublic(videoCreationFacade.isPublic())
                .setIspubliclink(videoCreationFacade.isPublicLink())
                .setKey(key)
                .setOwner(user);

        //Entity validations Validations
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Video>> constraintViolations = validator.validate(video);
        if(constraintViolations.size() > 0){
            List<String> errorMessages = new ArrayList<>();
            for(ConstraintViolation<Video> constraintViolation : constraintViolations){
                errorMessages.add(constraintViolation.getMessage());
            }
            throw new VideoException(EnumError.VIDEO_ERRORS, "upload video", errorMessages);
        }

        //Save video file
        try {
            //transfer video to the video library
            file.transferTo(new File(videoLibrary.getFile().getAbsolutePath() + File.separator + key));
        } catch (Exception e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(EnumError.VIDEO_UPLOAD_SAVE_FILE_ON_SERVER);
            throw new VideoException(EnumError.VIDEO_ERRORS, "upload video save on server", errorMessages);
        }

        //Create db entry for the video (generate key associated to the video)
        video = videoRepository.saveAndFlush(video);

        //Send back the video key
        return video.getKey();
    }
    //endregion

    //region private methods
    /**
     * Method to test video validity
     * @param file
     * @throws VideoException
     */
    private void CheckVideoValidity(MultipartFile file) throws VideoException {
        List<String> errorMessages = new ArrayList<>();
        //File empty
        if(file == null || file.isEmpty()){
            errorMessages.add(EnumError.VIDEO_UPLOAD_FILE_EMPTY);
        }
        //File type : allowed -> mp4
        if(!FilenameUtils.getExtension(file.getOriginalFilename()).equals("mp4")){
            errorMessages.add(EnumError.VIDEO_UPLOAD_NO_VALID_TYPE);
        }
        //File max size : 500Mo
        if(file.getSize() > Integer.parseInt(environment.getProperty("upload.maxFileSize"))){
            errorMessages.add(String.format(EnumError.VIDEO_UPLOAD_TOO_HEAVY, FileUtils.byteCountToDisplaySize(Integer.parseInt(environment.getProperty("upload.maxFileSize")))));
        }

        //Raised potential errors
        if(errorMessages.size()>0){
            throw new VideoException(EnumError.VIDEO_ERRORS,"upload video",errorMessages);
        }
    }
    //endregion

}
