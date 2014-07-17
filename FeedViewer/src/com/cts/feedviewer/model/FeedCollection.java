
package com.cts.feedviewer.model;

import java.util.ArrayList;

/**
 * Custom collection to store Feeds in an ArrayList
 * @author 330016
 *
 */
public class FeedCollection {
    private ArrayList<Feed> feedCollectionArray;

    private String pageTitle;

    private static FeedCollection feedCollection;

    private FeedCollection() {
        feedCollectionArray = new ArrayList<Feed>();
    }

    public static FeedCollection getFeedCollection() {
        if (feedCollection == null) {
            feedCollection = new FeedCollection();
        }
        return feedCollection;
    }

    public void addFeed(Feed feed) {
        feedCollectionArray.add(feed);
    }

    public ArrayList<Feed> getFeedCollectionArray() {
        return feedCollectionArray;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
    
    public void clearAll(){
    	feedCollectionArray.clear();
    }
}
