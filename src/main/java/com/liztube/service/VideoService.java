package com.liztube.service;

import com.liztube.business.SearchForVideosBusiness;
import com.liztube.business.VideoBusiness;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;
import com.liztube.utils.EnumVideoOrderBy;
import com.liztube.utils.GroupRoles;
import com.liztube.utils.facade.video.GetVideosFacade;
import com.liztube.utils.facade.video.VideoCreationFacade;
import com.liztube.utils.facade.video.VideoSearchFacade;
import org.springframework.beans.factory.annotation.Autowired;
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
                           @RequestParam("isPublicLink") boolean isPublicLink) throws UserNotFoundException, VideoException {
        VideoCreationFacade videoCreationFacade = new VideoCreationFacade().setTitle(title).setDescription(description).setPublic(isPublic).setPublicLink(isPublicLink);
        return videoBusiness.uploadVideo(file, videoCreationFacade);
    }

    /**
     * Get video for home page : /api/video(?
     * page=[int]&
     * pagination=[int]&
     * user=[id]&
     * q=[string encoded])
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public GetVideosFacade getVideosForHomePage(@RequestParam(value = "page", required = false) Integer page,
                                                @RequestParam(value = "pagination", required = false) Integer pagination,
                                                @RequestParam(value = "user", required = false) Integer userId,
                                                @RequestParam(value = "q", required = false) String query) {
        VideoSearchFacade videoSearchFacade = new VideoSearchFacade()
                .setPage((page == null) ? 1 : page)
                .setUserId((userId == null) ? 0 : userId)
                .setKeyword((query == null) ? "" : query)
                .setPagination((pagination == null) ? 0 : pagination)
                .setOrderBy(EnumVideoOrderBy.HOMESUGGESTION);
        return searchForVideosBusiness.GetVideos(videoSearchFacade);
    }

    /**
     * Get video ordered by ... : /api/video/[mostviewed | mostrecent | mostshared](?
     * page=[int]&
     * pagination=[int]&
     * user=[id]&
     * q=[string encoded])
     */
    @RequestMapping(value = "/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public GetVideosFacade getVideosOrderByMostViewed(  @PathVariable(value = "orderBy") String orderBy,
                                                        @RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "pagination", required = false) Integer pagination,
                                                        @RequestParam(value = "user", required = false) Integer userId,
                                                        @RequestParam(value = "q", required = false) String query) {
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
    }
}
