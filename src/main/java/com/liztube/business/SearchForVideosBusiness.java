package com.liztube.business;

import com.liztube.entity.Video;
import com.liztube.repository.VideoRepository;
import com.liztube.utils.facade.video.GetVideosFacade;
import com.liztube.utils.facade.video.VideoDataFacade;
import com.liztube.utils.facade.video.VideoSearchFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Business to search for videos
 */
@Component
public class SearchForVideosBusiness {

    @Autowired
    AuthBusiness authBusiness;
    @Autowired
    VideoRepository videoRepository;

    @Autowired
    Environment environment;

    /**
     * Get videos according to a set of options
     * page attribute : [1;infinite]
     * @return
     */
    public GetVideosFacade GetVideos(VideoSearchFacade videoSearchFacade){

        //Get number of videos to return (get value send or get default value)
        int pagination = (videoSearchFacade.getPagination() == 0) ? Integer.parseInt(environment.getProperty("video.default.pagination")) : videoSearchFacade.getPagination();
        //Get page asked for (we substract 1 because page go from 1 to infinite and pagination engine need to start to 0)
        int page = videoSearchFacade.getPage() - 1;

        //Search
        final Pageable pageRequest = new PageRequest(
                page, pagination, new Sort(
                new Sort.Order(Sort.Direction.DESC, "creationdate")
        ));
        Page pageFound = videoRepository.findAll(pageRequest);

        //Get video facade
        List<VideoDataFacade> videosFound = new ArrayList<>();
        List<Video> videos = pageFound.getContent();
        String videoUrl = environment.getProperty("video.url");
        videos.forEach((video) -> videosFound.add(new VideoDataFacade()
                        .setUrl(videoUrl + video.getKey())
                        .setTitle(video.getTitle())
                        .setDescription(video.getDescription())
                        .setViews(video.getViews().size())
        ));

        return new GetVideosFacade()
                .setVideosTotalCount(pageFound.getTotalElements())
                .setVideosTotalPage(pageFound.getTotalPages())
                .setCurrentPage(videoSearchFacade.getPage())
                .setVideos(videosFound);
    }
}
