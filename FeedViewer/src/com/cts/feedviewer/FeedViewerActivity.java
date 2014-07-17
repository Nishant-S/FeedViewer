
package com.cts.feedviewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.cts.feedviewer.model.FeedCollection;
import com.cts.feedviewer.parser.JsonParser;
import com.cts.feedviewer.parser.JsonParserListener;

/**
 * This class is the only Activity in the app, which is also the listener for
 * parser events
 * 
 * @author 330016
 */
public class FeedViewerActivity extends Activity implements JsonParserListener, OnScrollListener {

    // View to show the feeds
    private ListView feedList;

    // Collection of model Feed classes, which we will pass to FeedAdapter
    private FeedCollection feedCollection;

    // Adapter to show each of the row items of ListView
    private FeedAdapter feedAdapter;

    // Custom class, which handles all Json parsing
    private JsonParser jsonParser;

    private boolean listViewBusy;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_viewer);
        // we are passing the context as well as listener(Activity itself)
        //Json parsing will happen in a parallel thread and as soon as parsing completes,
        //we will be notified of the event.
        jsonParser = new JsonParser(this, this);
        jsonParser.parseData();
        Resources resources = getResources();
        feedCollection = FeedCollection.getFeedCollection();
        setTitle(feedCollection.getPageTitle());
        feedList = (ListView)findViewById(R.id.feedlist);
        // Pass feedCollection to adapter; right now it is empty.
        feedAdapter = new FeedAdapter(feedCollection, this);
        feedList.setAdapter(feedAdapter);
    }

    /**
     * Parsing is done now. Adapter need to notified, so that it can refer data
     * set from latest collection. Also, we will cancel the progress dialog.
     */
    @Override
    public void onParsingCompleteNotify() {
        setTitle(feedCollection.getPageTitle());
        feedAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Here, we will check that whether scrolling is currently happening or not.
     * if scrolling is not happening, then only we will tell ImageDownLoader to download an image. 
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE :
                listViewBusy = false;
                feedAdapter.notifyDataSetChanged();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL :
                listViewBusy = true;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING :
                listViewBusy = true;
                break;
        }
    }
    public boolean isListViewBusy() {
        return listViewBusy;
    }

}
