
package com.cts.feedviewer;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cts.feedviewer.model.Feed;
import com.cts.feedviewer.model.FeedCollection;

/**
 * This is the Adapter class, which takes ArrayList<Feed> and creates view for each row.
 * @author 330016
 *
 */
public class FeedAdapter extends BaseAdapter {
    private ArrayList<Feed> feedCollectionArray;

    private Context context;

    public FeedAdapter(FeedCollection feedCollection, Context context) {
        this.feedCollectionArray = feedCollection.getFeedCollectionArray();
        this.context = context;
    }

    public void setItems(ArrayList<Feed> feedCollection) {
        this.feedCollectionArray = feedCollection;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return feedCollectionArray.size();
    }

    @Override
    public Object getItem(int position) {
        return feedCollectionArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Feed currentFeed = feedCollectionArray.get(position);
        //We will use holder pattern here. Holder will have a reference to all the views for a row layout.
        //We will set this holder to the root container of each row. If view has no holder attached, then a new holder will be created.
        FeedViewHolder holder;
        if (convertView == null) {
            view = parent.inflate(parent.getContext(), R.layout.feed_listitem, null);
            holder = new FeedViewHolder();
            holder.feedImage = (ImageView)view.findViewById(R.id.feedimage);
            holder.feedTitle = (TextView)view.findViewById(R.id.feedheader);
            holder.feedDesc = (TextView)view.findViewById(R.id.feeddesc);
            view.setTag(holder);
        } else {
            holder = (FeedViewHolder)view.getTag();
        }
        //We will check here whether scrolling is happening or stopped. If stopped, then we tell ImageDownloader to download the images.
        holder.populate(currentFeed, ((FeedViewerActivity)context).isListViewBusy());
        return view;
    }

    static class FeedViewHolder {
        public ImageView feedImage;

        public TextView feedTitle;

        public TextView feedDesc;

        void populate(Feed currentFeed, boolean isBusy) {
            String title = currentFeed.getTitle();
            String description = currentFeed.getDescription();
            String imageUrl = currentFeed.getImageUrl();
            // Here, we need to set the visibility GONE and VISIBLE based on the
            // tags.
            if (imageUrl.equals("null")) {
                feedImage.setVisibility(View.GONE);
            } else {
                feedImage.setVisibility(View.VISIBLE);
            }

            if (title.equals("null")) {
                feedTitle.setVisibility(View.GONE);
            } else {
                feedTitle.setVisibility(View.VISIBLE);
            }

            if (description.equals("null")) {
                feedDesc.setVisibility(View.GONE);
            } else {
                feedDesc.setVisibility(View.VISIBLE);
            }

            feedTitle.setText(title);
            feedDesc.setText(description);
            if (!isBusy) {
                //ImageDownloader is a Apache utility class, which we are using here for lazy loading of bitmaps.
                ImageDownloader imageDownloader = new ImageDownloader();
                imageDownloader.download(imageUrl, feedImage);
            }
        }

    }

}
