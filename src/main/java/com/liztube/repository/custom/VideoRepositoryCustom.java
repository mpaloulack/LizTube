package com.liztube.repository.custom;

import com.liztube.entity.Video;
import com.liztube.utils.EnumVideoOrderBy;
import com.liztube.utils.facade.video.GetVideosFacade;
import com.liztube.utils.facade.video.VideoSearchFacadeForRepository;
import javafx.util.Pair;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by laurent on 05/04/15.
 */
public interface VideoRepositoryCustom {
    /**
     * Get videos according to a set of params
     * @param vFacade
     * @return list of videos found for the page asked for and total count of videos found
     */
    Pair<List<Video>, Long> findVideosByCriteria(VideoSearchFacadeForRepository vFacade);
}
