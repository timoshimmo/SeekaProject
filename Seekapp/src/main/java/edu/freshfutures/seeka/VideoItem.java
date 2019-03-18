package edu.freshfutures.seeka;

/**
 * Created by tokmang on 5/7/2016.
 */
public class VideoItem {

    private String title;
    private String descriptionUpload;
    private String descriptionViews;
    private String thumbnailURL;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionUploaded() {
        return descriptionUpload;
    }

    public void setDescriptionUploaded(String description) {
        this.descriptionUpload = description;
    }

    public String getDescriptionViews() {
        return descriptionViews;
    }

    public void setDescriptionViews(String description) {
        this.descriptionViews = description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnail) {
        this.thumbnailURL = thumbnail;
    }
}
