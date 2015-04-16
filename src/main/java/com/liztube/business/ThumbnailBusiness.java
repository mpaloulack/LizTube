package com.liztube.business;

import com.liztube.exception.ThumbnailException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;
import com.liztube.utils.thumbnails.ThumbnailResizing;
import org.jcodec.api.FrameGrab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Thumbnail business
 */
@Component
public class ThumbnailBusiness {
    @Autowired
    VideoBusiness videoBusiness;

    public ClassPathResource videoLibrary = new ClassPathResource("VideoLibrary/");
    public ClassPathResource videoThumbnailsLibrary = new ClassPathResource("VideoThumbnailsLibrary/");
    public ClassPathResource liztubeDefaultContent = new ClassPathResource("LiztubeDefaultContent/");

    /**
     * 0 -> server absolute library path
     * 1 -> file separator
     * 2 -> file name
     */
    public String filePathForFormat = "%s%s%s";

    public static final String VIDEO_CREATE_DEFAULT_THUMBNAIL = "An unexpected error occured when trying to generate default thumbnail.";
    public static final String VIDEO_GET_DEFAULT_THUMBNAIL = "An unexpected error occured when trying to get default thumbnail.";
    public static final String VIDEO_DEFAULT_THUMBNAIL_DEFAULT = "liztube";
    public static final String VIDEO_DEFAULT_THUMBNAIL_UNAVAILABLE = "unavailable";
    public static final String VIDEO_DEFAULT_THUMBNAIL_IMAGE_TYPE = "png";
    public static final String VIDEO_DEFAULT_THUMBNAIL_DEFAULT_IMAGE_SUFFIX = "_default." + VIDEO_DEFAULT_THUMBNAIL_IMAGE_TYPE;
    public static final int VIDEO_DEFAULT_THUMBNAIL_FRAME = 25;

    /**
     * Generate default thumbnail
     * @param key
     * @throws ThumbnailException
     */
    public void createDefaultThumbnail(String key) throws ThumbnailException {
        try {
            //Get video file on the server
            File file = new File(String.format(filePathForFormat, videoLibrary.getFile().getAbsolutePath(), File.separator, key));
            //Capture thumbnail
            BufferedImage thumbnail = FrameGrab.getFrame(file, VIDEO_DEFAULT_THUMBNAIL_FRAME);
            //Adapt thumbnail size
            thumbnail = ThumbnailResizing.adaptThumbnailSize(thumbnail);
            //Save thumbnail
            ImageIO.write(thumbnail, VIDEO_DEFAULT_THUMBNAIL_IMAGE_TYPE, new File(String.format(filePathForFormat, videoThumbnailsLibrary.getFile().getAbsolutePath(), File.separator, key + VIDEO_DEFAULT_THUMBNAIL_DEFAULT_IMAGE_SUFFIX)));
        }catch (Exception e) {
            e.printStackTrace();
            throw new ThumbnailException("Thumbnail - Create default video thumbnail", VIDEO_CREATE_DEFAULT_THUMBNAIL);
        }
    }

    /**
     * Get video default thumbnail
     * @param key
     */
    public byte[] getThumbnail(String key) throws ThumbnailException, VideoException, UserNotFoundException {

        //Test if video exists and if user has enough rights
        try{
            videoBusiness.videoCanBeGetted(key);
        }catch (Exception e){
            e.printStackTrace();
            return getDefaultLiztubeThumbnail(VIDEO_DEFAULT_THUMBNAIL_UNAVAILABLE);
        }

        //Get video thumbnail
        try {
            // Prepare buffered image.
            BufferedImage img = ImageIO.read(new File(String.format(filePathForFormat, videoThumbnailsLibrary.getFile().getAbsolutePath(), File.separator, key + VIDEO_DEFAULT_THUMBNAIL_DEFAULT_IMAGE_SUFFIX)));
            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            // Write to output stream
            ImageIO.write(img, "png", bao);
            return bao.toByteArray();
        } catch (Exception e) {
            return getDefaultLiztubeThumbnail(VIDEO_DEFAULT_THUMBNAIL_DEFAULT);
        }
    }

    /**
     * Get default liztube thumbnail
     * @return
     * @throws ThumbnailException
     */
    private byte[] getDefaultLiztubeThumbnail(String type) throws ThumbnailException {
        try {
            // Prepare buffered image.
            BufferedImage img = ImageIO.read(new File(String.format(filePathForFormat, liztubeDefaultContent.getFile().getAbsolutePath(), File.separator, type + VIDEO_DEFAULT_THUMBNAIL_DEFAULT_IMAGE_SUFFIX)));
            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            // Write to output stream
            ImageIO.write(img, "png", bao);
            return bao.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ThumbnailException("Thumbnail - An error occurred when trying to get video thumbnail", VIDEO_GET_DEFAULT_THUMBNAIL);
        }
    }
}