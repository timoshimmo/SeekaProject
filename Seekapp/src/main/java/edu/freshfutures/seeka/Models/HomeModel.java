package edu.freshfutures.seeka.Models;

/**
 * Created by tokmang on 9/13/2016.
 */
public class HomeModel {

    private String articleImage;
    private String articleTitle;
    private String articleTopic;
    private String articleDetails;
    private int articleId;
    private String link;
    private int likeValue;
    private String likeStatus;
    private int reviewed;
    private int shared;
    private int cachedData;

    public HomeModel() {
        super();
    }

    public void setArticleImage(String img) {
        this.articleImage = img;
    }

    public void setArticleTitle(String artTtl) {
        this.articleTitle = artTtl;
    }

    public void setArticleTopic(String artTpc) {
        this.articleTopic = artTpc;
    }

    public void setArticleDetails(String artDtls) {
        this.articleDetails = artDtls;
    }

    public void setArticleLink(String link) {
        this.link = link;
    }

    public void setLikeValue(int likeStatus) {
        this.likeValue = likeStatus;
    }

    public void setLikedStatus(String likedStatus) {
        this.likeStatus = likedStatus;
    }

    public void setReviewed(int reviewed) {
        this.reviewed = reviewed;
    }

    public void setSharedValue(int shared) {
        this.shared = shared;
    }

    public void setArticleId(int id) {
        this.articleId = id;
    }

    public void setCachedData(int cached) {
        this.cachedData = cached;
    }



    public String getArticleImage() {
        return this.articleImage;
    }

    public String getArticleTitle() {
        return this.articleTitle;
    }

    public String getArticleTopic() {
        return this.articleTopic;
    }

    public String getArticleDetails() {
        return this.articleDetails;
    }

    public String getArticleLink() {
        return this.link;
    }

    public int getLikeValue() {
        return this.likeValue;
    }

    public String getLikedStatus() {
        return this.likeStatus;
    }

    public int getReviewed() {
        return this.reviewed;
    }

    public int getShared() {
        return this.shared;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public int getCachedData() {
        return this.cachedData;
    }


}
