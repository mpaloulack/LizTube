package com.liztube.utils.facade.video;

/**
 * Facade which contains all the options to search for videos
 */
public class VideoSearchFacade {

    //region attributes
    /**
     * Keyword from search bar
     */
    private String keyword;
    /**
     * Get videos most viewed if true else get lastest videos
     */
    private boolean videosMostViewed;
    /**
     * Page asked for
     */
    private int page;
    /**
     * Number of videos by page asked for
     * Default value set in the application properties if = 0
     */
    private int pagination;
    /**
     * Search video for a specific user
     */
    private long userId;
    //endregion

    //region getter/setter

    public String getKeyword() {
        return keyword;
    }

    public VideoSearchFacade setKeyword(String keyword) {
        this.keyword = keyword; return this;
    }

    public boolean isVideosMostViewed() {
        return videosMostViewed;
    }

    public VideoSearchFacade setVideosMostViewed(boolean videosMostViewed) {
        this.videosMostViewed = videosMostViewed; return this;
    }

    public int getPage() {
        return page;
    }

    public VideoSearchFacade setPage(int page) {
        this.page = page; return this;
    }

    public int getPagination() {
        return pagination;
    }

    public VideoSearchFacade setPagination(int pagination) {
        this.pagination = pagination; return this;
    }

    public long getUserId() {
        return userId;
    }

    public VideoSearchFacade setUserId(long userId) {
        this.userId = userId; return this;
    }

    //endregion
}
