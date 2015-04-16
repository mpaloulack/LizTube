package com.liztube.service;

import com.liztube.business.SearchForVideosBusiness;
import com.liztube.business.ThumbnailBusiness;
import com.liztube.business.VideoBusiness;
import com.liztube.exception.ServiceException;
import com.liztube.exception.ThumbnailException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;
import com.liztube.exception.exceptionType.PublicException;
import com.liztube.utils.EnumVideoOrderBy;
import com.liztube.utils.GroupRoles;
import com.liztube.utils.facade.UserForRegistration;
import com.liztube.utils.facade.video.GetVideosFacade;
import com.liztube.utils.facade.video.VideoCreationFacade;
import com.liztube.utils.facade.video.VideoDataFacade;
import com.liztube.utils.facade.video.VideoSearchFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    SearchForVideosBusiness searchForVideosBusiness;
    @Autowired
    ThumbnailBusiness thumbnailBusiness;

    /**
     * Upload a video
     * @param file
     */
    @PreAuthorize(GroupRoles.AUTHENTICATED)
    @RequestMapping(value="/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public String uploadVideo(@RequestParam(value="file", required=true) MultipartFile file,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("isPublic") boolean isPublic,
                           @RequestParam("isPublicLink") boolean isPublicLink) throws UserNotFoundException, VideoException, ThumbnailException, ServiceException {
        try{
            VideoCreationFacade videoCreationFacade = new VideoCreationFacade().setTitle(title).setDescription(description).setPublic(isPublic).setPublicLink(isPublicLink);
            return videoBusiness.uploadVideo(file, videoCreationFacade);
        }catch (PublicException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("Upload video");
        }
    }

    /**
     * Get video data
     * @param key
     * @return
     */
    @RequestMapping(value="/{key}", method = RequestMethod.GET)
    @ResponseBody
    public VideoDataFacade get(@PathVariable(value = "key") String key) throws VideoException, UserNotFoundException, ServiceException {
        try{
            return videoBusiness.get(key);
        }catch (PublicException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("Get video data");
        }
    }

    /**
     * Update video data
     * @param videoDataFacade
     * @return
     */
    @PreAuthorize(GroupRoles.AUTHENTICATED)
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String update(@RequestBody VideoDataFacade videoDataFacade) throws VideoException, UserNotFoundException, ServiceException {
        try{
            return videoBusiness.update(videoDataFacade);
        }catch (PublicException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("Update video");
        }
    }

    /**
     * Get video ordered by ... : /api/video/[mostviewed | mostrecent | mostshared | q (for home suggestion)](?
     * page=[int]&
     * pagination=[int]&
     * user=[id]&
     * q=[string encoded])
     */
    @RequestMapping(value = "/search/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public GetVideosFacade getVideosOrderByMostViewed(  @PathVariable(value = "orderBy") String orderBy,
                                                        @RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "pagination", required = false) Integer pagination,
                                                        @RequestParam(value = "user", required = false) Integer userId,
                                                        @RequestParam(value = "q", required = false) String query) throws ServiceException {
        try{
            EnumVideoOrderBy enumVideoOrderBy = EnumVideoOrderBy.HOMESUGGESTION;
            switch (orderBy){
                case "mostviewed":
                    enumVideoOrderBy = EnumVideoOrderBy.MOSTVIEWED;
                    break;
                case "mostrecent":
                    enumVideoOrderBy = EnumVideoOrderBy.MOSTRECENT;
                    break;
                case "mostshared":
                    enumVideoOrderBy = EnumVideoOrderBy.MOSTSHARED;
                    break;
            }
            VideoSearchFacade videoSearchFacade = new VideoSearchFacade()
                    .setPage((page == null) ? 1 : page)
                    .setUserId((userId == null) ? 0 : userId)
                    .setKeyword((query == null) ? "" : query)
                    .setPagination((pagination == null) ? 0 : pagination)
                    .setOrderBy(enumVideoOrderBy);
            return searchForVideosBusiness.GetVideos(videoSearchFacade);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("Get videos");
        }
    }

    /**
     * Get video thumbnail
     * @param key
     * @return
     * @throws ThumbnailException
     */
    @RequestMapping(value = "/thumbnail/{key}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getThumbnail(@PathVariable(value = "key") String key) throws ThumbnailException, VideoException, UserNotFoundException, ServiceException {
        try{
            return thumbnailBusiness.getThumbnail(key);
        }catch (PublicException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("Get thumbnail");
        }
    }
}
