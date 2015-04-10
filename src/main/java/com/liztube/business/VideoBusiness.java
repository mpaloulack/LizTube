package com.liztube.business;

import com.liztube.entity.UserLiztube;
import com.liztube.entity.Video;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;
import com.liztube.repository.VideoRepository;
import com.liztube.utils.EnumError;
import com.liztube.utils.facade.video.GetVideosFacade;
import com.liztube.utils.facade.video.VideoCreationFacade;
import com.liztube.utils.facade.video.VideoDataFacade;
import com.liztube.utils.facade.video.VideoSearchFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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

    public static final String VIDEO_UPLOAD_FILE_EMPTY     = "File is empty.";
    public static final String VIDEO_UPLOAD_NO_VALID_TYPE  = "Not valid type of file uploaded.";
    public static final String VIDEO_UPLOAD_TOO_HEAVY      = "File size exceed {0} Mo.";
    public static final String VIDEO_NOT_FOUND = "Video not found";
    public static final String VIDEO_NOT_AVAILABLE = "Video not available. This is a private video.";
    public static final String VIDEO_UPDATE_USER_IS_NOT_VIDEO_OWNER = "Don't have sufficient rights to edit this video.";

    @Autowired
    Environment environment;

    //region public methods

    /**
     * Get video data and test rights (private, public and user connected is owner)
     * @return
     */
    public VideoDataFacade get(String key) throws VideoException, UserNotFoundException {
        Video video = videoRepository.findByKey(key);
        if(video == null){
            throw new VideoException("Get video - video not found", Arrays.asList(VIDEO_NOT_FOUND));
        }
        if(!video.getIspublic() && !video.getIspubliclink()){
            UserLiztube user = authBusiness.getConnectedUser(false);
            if(user == null || user.getId() != video.getOwner().getId()){
                throw new VideoException("Get video - video not available isPublic: "+video.getIspublic()+", isPublicLink: "+video.getIspubliclink(), Arrays.asList(VIDEO_NOT_AVAILABLE));
            }
        }
        return new VideoDataFacade()
                .setKey(video.getKey())
                .setTitle(video.getTitle())
                .setDescription(video.getDescription())
                .setCreationDate(video.getCreationdate())
                .setOwnerId(video.getOwner().getId())
                .setOwnerPseudo(video.getOwner().getPseudo())
                .setPublic(video.getIspublic())
                .setPublicLink(video.getIspubliclink())
                .setViews(video.getViews().size());
    }

    /**
     * Update video data
     * @return
     */
    public String update(VideoDataFacade videoDataFacade) throws VideoException, UserNotFoundException {
        Video video = videoRepository.findByKey(videoDataFacade.getKey());
        if(video == null){
            throw new VideoException("Update video - video not found", Arrays.asList(VIDEO_NOT_FOUND));
        }
        UserLiztube user = authBusiness.getConnectedUser(false);
        if(user == null || user.getId() != video.getOwner().getId()){
            throw new VideoException("Update video - user don't have right to edit video", Arrays.asList(VIDEO_UPDATE_USER_IS_NOT_VIDEO_OWNER));
        }
        video.setTitle(videoDataFacade.getTitle())
                .setDescription(videoDataFacade.getDescription())
                .setIspublic(videoDataFacade.isPublic())
                .setIspubliclink(videoDataFacade.isPublicLink());

        //Entity validations
        CheckVideoEntityValidity(video);

        //Update video in DB
        video = videoRepository.saveAndFlush(video);

        //Send back the video key
        return video.getKey();
    }

    /**
     *  Upload a video : validity checks, save file, create db entry
     * @param file
     * @param videoCreationFacade
     */
    public String uploadVideo(MultipartFile file, VideoCreationFacade videoCreationFacade) throws UserNotFoundException, VideoException {

        //Get connected user
        UserLiztube user = authBusiness.getConnectedUser(true);

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
                .setOwner(user)
                .setCreationdate(Timestamp.valueOf(LocalDateTime.now()));

        //Entity validations
        CheckVideoEntityValidity(video);

        //Save video file
        try {
            //transfer video to the video library
            file.transferTo(new File(videoLibrary.getFile().getAbsolutePath() + File.separator + key));
        } catch (Exception e) {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(EnumError.VIDEO_UPLOAD_SAVE_FILE_ON_SERVER);
            throw new VideoException("copy on the server", errorMessages);
        }

        //---------
        /*BufferedImage frame = FrameGrab.getFrame(new File("/Users/jovi/Movies/test.mp4"), 3);
        ImageIO.write(frame, "bmp", new File("/Users/jovi/Desktop/frames/" + video.getKey() + ".bmp"));*/
        //---------

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
            errorMessages.add(VIDEO_UPLOAD_FILE_EMPTY);
        }
        //File type : allowed -> mp4
        if(!FilenameUtils.getExtension(file.getOriginalFilename()).equals("mp4")){
            errorMessages.add(VIDEO_UPLOAD_NO_VALID_TYPE);
        }
        //File max size : 500Mo
        if(file.getSize() > Integer.parseInt(environment.getProperty("upload.maxFileSize"))){
            errorMessages.add(String.format(VIDEO_UPLOAD_TOO_HEAVY, FileUtils.byteCountToDisplaySize(Integer.parseInt(environment.getProperty("upload.maxFileSize")))));
        }

        //Raised potential errors
        if(errorMessages.size()>0){
            throw new VideoException("check validity",errorMessages);
        }
    }

    /**
     * Check video entity validity
     * @param video
     */
    private void CheckVideoEntityValidity(Video video) throws VideoException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Video>> constraintViolations = validator.validate(video);
        if(constraintViolations.size() > 0){
            List<String> errorMessages = new ArrayList<>();
            for(ConstraintViolation<Video> constraintViolation : constraintViolations){
                errorMessages.add(constraintViolation.getMessage());
            }
            throw new VideoException("save video check attributes validity", errorMessages);
        }
    }
    //endregion

}
