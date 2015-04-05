package com.liztube.utils.facade.video;

/**
 * Video data to send to the client when searching for example
 */
public class VideoDataFacade {

    //region attributes
    private String title;
    private String description;
    private int views;
    private String url;
    private String ownerPseudo;
    private long ownerId;
    private boolean isPublic;
    private boolean isPublicLink;
    //endregion

    //region getter/setter

    public String getUrl() {
        return url;
    }

    public VideoDataFacade setUrl(String url) {
        this.url = url; return this;
    }

    public String getTitle() {
        return title;
    }

    public VideoDataFacade setTitle(String title) {
        this.title = title; return this;
    }

    public String getDescription() {
        return description;
    }

    public VideoDataFacade setDescription(String description) {
        this.description = description; return this;
    }

    public int getViews() {
        return views;
    }

    public VideoDataFacade setViews(int views) {
        this.views = views; return this;
    }

    public String getOwnerPseudo() {
        return ownerPseudo;
    }

    public VideoDataFacade setOwnerPseudo(String ownerPseudo) {
        this.ownerPseudo = ownerPseudo; return this;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public VideoDataFacade setOwnerId(long ownerId) {
        this.ownerId = ownerId; return this;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public VideoDataFacade setPublic(boolean isPublic) {
        this.isPublic = isPublic; return this;
    }

    public boolean isPublicLink() {
        return isPublicLink;
    }

    public VideoDataFacade setPublicLink(boolean isPublicLink) {
        this.isPublicLink = isPublicLink; return this;
    }

    //endregion

}
