
package com.cts.feedviewer.model;

/**
 * Model class to hold each of the feed row data
 * @author 330016
 *
 */
public class Feed {
    private String title;

    private String description;

    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public Feed setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Feed setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Feed setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

}
